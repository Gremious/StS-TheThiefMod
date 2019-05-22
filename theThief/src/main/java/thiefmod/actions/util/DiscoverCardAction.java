package thiefmod.actions.util;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import thiefmod.actions.common.MakeStolenCardAction;
import thiefmod.patches.DiscoveryPatch;

import java.util.List;
import java.util.stream.Collectors;

public class DiscoverCardAction extends AbstractGameAction implements CustomSavable<List<String>> {
    protected boolean retrieveCard = false;
    public static CardGroup cardsDiscoveredThisCombat = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    protected static List<String> idList;
    
    protected CardGroup cardList= new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    protected int amount;
    protected int copies;
    protected boolean upgraded;
    protected boolean stolen;
    protected Integer costForTurn;
    
    public DiscoverCardAction(final CardGroup cardList) {
        this(cardList, 3, false, null, 1, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final int amount) {
        this(cardList, amount, false, null, 1, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final int amount, int copies) {
        this(cardList, amount, false, null, copies, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final boolean upgraded) {
        this(cardList, 3, upgraded, null, 1, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final int amount, final boolean upgraded) {
        this(cardList, amount, upgraded, null, 1, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final boolean upgraded, final int copies) {
        this(cardList, 3, upgraded, null, copies, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final int amount, final boolean upgraded, final int copies) {
        this(cardList, amount, upgraded, null, copies, false);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final Integer costForTurn) {
        this(cardList, 3, false, costForTurn, 1, false);
    }
    
    public DiscoverCardAction(CardGroup cardList,
                              final int amount,
                              final boolean upgraded,
                              final Integer costForTurn,
                              final int copies,
                              final boolean stolen) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        
        this.cardList.group.addAll(cardList.group);
        this.amount = amount;
        this.upgraded = upgraded;
        this.costForTurn = costForTurn;
        this.copies = copies;
        this.stolen = stolen;
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
                
                if (!stolen) {
                    for (int i = 0; i < copies; i++) {
                        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard.makeStatEquivalentCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard.makeStatEquivalentCopy(), Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        }
                    }
                } else {
                    for (int i = 0; i < copies; i++) {
                        AbstractCard stemp = MakeStolenCardAction.makeStolenCard(disCard.makeStatEquivalentCopy());
                        if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(stemp, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        } else {
                            AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(stemp, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                        }
                    }
                }
                
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        
        tickDuration();
    }
    
    public static AbstractCard getLastCardDiscoveredThisCombat() {
        return cardsDiscoveredThisCombat.getTopCard();
    }
    
    public static CardGroup getAllCardsDiscoveredThisCombat() {
        return cardsDiscoveredThisCombat;
    }
    
    @Override
    public List<String> onSave() {
        idList = cardsDiscoveredThisCombat.group.stream().map(c -> c.cardID).collect(Collectors.toList());
        return idList;
    }
    
    @Override
    public void onLoad(List<String> strings) {
        if (!(idList == null) && !idList.isEmpty()) {
            for (String id : idList) {
                cardsDiscoveredThisCombat.addToTop(CardLibrary.getCard(id));
            }
        }
    }
}
