package thiefmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;
import java.util.function.Predicate;

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

            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.discardPile, Predicate.isEqual(c)));
            System.out.println("Discard to hand added.");

            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.drawPile, Predicate.isEqual(c)));
            System.out.println("Draw to hand added.");

            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.exhaustPile, Predicate.isEqual(c)));
            System.out.println("Exhaust to hand added.");

            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.limbo, Predicate.isEqual(c))); // ? Who knows.
            System.out.println("Limbo to hand added.");
        }
        isDone = true;
    }
}
