package thiefmod.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thiefmod.actions.util.DiscoverCardAction;
import thiefmod.patches.DiscoveryPatch;

public class DiscoverStolenCardAction extends DiscoverCardAction {
    public DiscoverStolenCardAction(CardGroup cardList, int amount, boolean upgraded, Integer costForTurn, int copies) {
        super(cardList, amount, upgraded, costForTurn, copies);
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (cardList.size() < amount) amount = cardList.size();
            if (cardList.size() <= 0) isDone = true;
            
            
            DiscoveryPatch.customDiscovery = true;
            
            DiscoveryPatch.amount = amount;
            DiscoveryPatch.upgraded = upgraded;
            DiscoveryPatch.cardGroupToDiscoverFrom = cardList;
            
            AbstractDungeon.cardRewardScreen.discoveryOpen();
            tickDuration();
            return;
        }
        
        if (!retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                cardsDiscoveredThisCombat.addToTop(disCard);
                disCard.current_x = -1000.0f * Settings.scale;
                if (costForTurn != null) disCard.setCostForTurn(costForTurn);
                
                for (int i = 0; i < copies; i++) {
                    AbstractDungeon.actionManager.addToBottom(new MakeStolenCardAction(disCard, AbstractDungeon.player.hand));
                }
                
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        
        tickDuration();
    }
}
