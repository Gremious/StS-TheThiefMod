package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.common.DrawPileToHandAction;
import thiefmod.actions.common.ExhaustToHandAction;

import java.util.ArrayList;

public class RetrievalAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private ArrayList<AbstractCard> cardsToReturn;

    public RetrievalAction(final ArrayList<AbstractCard> cardsToReturn) {
        this.cardsToReturn = cardsToReturn;
    }

    @Override
    public void update() {

        logger.info("Update start");
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        logger.info("Waited out");

        logger.info("cardsToRetun inside the action is " + cardsToReturn);

        for (AbstractCard iterateCard : cardsToReturn) {
            logger.info("For loop started");
          /*
           for (AbstractCard cardCheckDiscard : AbstractDungeon.player.discardPile.group) {
                if (cardCheckDiscard == iterateCard) {}
            }
          */
            AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(iterateCard));
            logger.info("Discard to hand added.");
            AbstractDungeon.actionManager.addToBottom(new DrawPileToHandAction(iterateCard));
            logger.info("Draw to hand added.");
            AbstractDungeon.actionManager.addToBottom(new ExhaustToHandAction(iterateCard));
            logger.info("Exhaust to hand added.");
        }
        logger.info("RetrievalAction is done.");

        isDone = true;
    }
}
