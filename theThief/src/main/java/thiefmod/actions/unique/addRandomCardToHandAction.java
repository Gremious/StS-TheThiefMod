package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;

import java.util.ArrayList;

public class addRandomCardToHandAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    // Blatantly stolen from the Beaked's Crazy rituals. Thank you very much guys!

    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private static ArrayList<AbstractCard> handCards;
    private static AbstractCard nextHandCard;

    public addRandomCardToHandAction(int amount) {

        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;

    }

    @Override
    public void update() {

        if (handCards == null) {
            handCards = CardLibrary.getAllCards();
        }

        getCard();

        // Wait action can't wait for more than 0.1s on fast mode, so just add a bunch of them
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(nextHandCard));
        this.isDone = true;

    }

    public AbstractCard getCard() {

        do {
            nextHandCard = handCards.get(AbstractDungeon.cardRandomRng.random(0, handCards.size() - 1))
                    .makeCopy();

        } while ((nextHandCard.rarity == AbstractCard.CardRarity.SPECIAL
                && nextHandCard.color.toString() != "INFINITE_BLACK")
                || nextHandCard.type == AbstractCard.CardType.CURSE
                || nextHandCard.rarity == AbstractCard.CardRarity.CURSE
                || nextHandCard.type == AbstractCard.CardType.STATUS ||
                // Yohane's summons require a special FriendlyMinions-enabled character.
                nextHandCard.cardID.startsWith("Yohane:Little_Demon_") ||
                // Mad Scientist's Mechanize apparently doesn't work
                nextHandCard.cardID == "MadScienceMod:Mechanize" ||
                // Pickle why is this ID not public, I'm far too lazy to use reflection
                nextHandCard.cardID == "ReplayTheSpireMod:??????????????????????" ||
                // blakkmod
                nextHandCard.cardID == "BlakkBlade" ||
                // blakkmod
                nextHandCard.cardID == "LegSlice"

        );

        nextHandCard.freeToPlayOnce = true;
        return nextHandCard;
    }
}