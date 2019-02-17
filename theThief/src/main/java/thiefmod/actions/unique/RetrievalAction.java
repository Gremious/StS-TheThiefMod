package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thiefmod.actions.Util.DrawPileToHandAction;
import thiefmod.actions.Util.ExhaustToHandAction;
import thiefmod.actions.Util.LimboToHandAction;

import java.util.List;

public class RetrievalAction extends AbstractGameAction {
    private int returnAmount;

    public RetrievalAction(final int returnAmount) {
        this.returnAmount = returnAmount;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        List<AbstractCard> cardsToReturn = AbstractDungeon.actionManager.cardsPlayedThisTurn.subList(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() - returnAmount, AbstractDungeon.actionManager.cardsPlayedThisTurn.size());

        System.out.println("There are " + returnAmount + " cards to return: " + cardsToReturn);

        for (AbstractCard c : cardsToReturn) {

            // I was now told StSlib has a MoveCardsAction. It is too late. I made all these actions. I don't wanna redo em.

            AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(c));
            System.out.println("Discard to hand added.");

            AbstractDungeon.actionManager.addToBottom(new DrawPileToHandAction(c));
            System.out.println("Draw to hand added.");

            AbstractDungeon.actionManager.addToBottom(new ExhaustToHandAction(c));
            System.out.println("Exhaust to hand added.");

            AbstractDungeon.actionManager.addToBottom(new LimboToHandAction(c)); // ? Who knows.
            System.out.println("Limbo to hand added.");
        }
        isDone = true; // Do i need this idk
    }
}
