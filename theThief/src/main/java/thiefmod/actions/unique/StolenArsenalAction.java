package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class StolenArsenalAction extends AbstractGameAction {
    private AbstractPlayer player;

    private static ArrayList<AbstractCard> drawCards;
    private static ArrayList<AbstractCard> discardCards;
    private static ArrayList<AbstractCard> exhaustCards;
    private static ArrayList<AbstractCard> handCards;

    public StolenArsenalAction(final AbstractPlayer player) {
        this.player = player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            handCards.addAll(player.hand.group);
            drawCards.addAll(player.drawPile.group);
            discardCards.addAll(player.discardPile.group);
            exhaustCards.addAll(player.exhaustPile.group);

            int handSize = player.hand.size();
            int drawSize = drawCards.size();
            int discardSize = discardCards.size();
            int exhaustSize = exhaustCards.size();

            AbstractCard shiv = CardLibrary.getCopy(Shiv.ID);
            shiv.exhaust = false;
            shiv.rawDescription = "Deal !D! damage.";

            for (AbstractCard c : handCards) {
                AbstractDungeon.player.hand.removeCard(c);

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, handSize));
            }
            for (AbstractCard c : drawCards) {
                AbstractDungeon.player.drawPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, handSize));
            }
            for (AbstractCard c : discardCards) {
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, handSize));
            }
            for (AbstractCard c : exhaustCards) {
                AbstractDungeon.player.exhaustPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(shiv, handSize));
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }
        tickDuration();
    }
}
