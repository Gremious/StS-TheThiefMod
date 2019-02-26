package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

//@Deprecated
public class TestAction extends AbstractGameAction {

    private static final String tipMSG = "Card to be removed.";
    private static final String tipMSGs = "Cards to be removed.";
    private int NumOfCards;

    private final ArrayList<AbstractCard> drawPileCards = new ArrayList<>();
    private final ArrayList<AbstractCard> handCards = new ArrayList<>();

    public TestAction(int NumOfCards) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.NumOfCards = NumOfCards;
    }


    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (NumOfCards == 1) {
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), NumOfCards, tipMSG, false, false, false, true);
            } else {
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), NumOfCards, tipMSGs, false, false, false, true);
            }
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            tickDuration();
        } else {
            if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {

                Iterator<AbstractCard> drawIter = AbstractDungeon.player.drawPile.group.iterator();
                Iterator<AbstractCard> handIter = AbstractDungeon.player.hand.group.iterator();

                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

                drawPileCards.addAll(AbstractDungeon.player.drawPile.group);
                handCards.addAll(AbstractDungeon.player.hand.group);

                for (AbstractCard card : drawPileCards) {
                    if (card.uuid == c.uuid) {
                        AbstractDungeon.player.drawPile.removeCard(card);
                    }
                }

                for (AbstractCard card : handCards) {
                    if (card.uuid == c.uuid) {
                        AbstractDungeon.player.hand.removeCard(card);
                    }
                }

                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.effectList.add(new PurgeCardEffect(c));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
            tickDuration();
        }
    }
}