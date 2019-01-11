package thiefmod.actions.unique;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import thiefmod.ThiefMod;
import thiefmod.actions.unique.QueueCardFrontAction;

public class addRandomCardToHandAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    public static ArrayList<AbstractCard> handCards;
    public static AbstractCard nextHandCard;
    private boolean cardOffset;

    public addRandomCardToHandAction(int amount, boolean cardOffset) {

        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.cardOffset = cardOffset;

    }

    @Override
    public void update() {

        if (handCards == null)
            handCards = CardLibrary.getAllCards();

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
                // Servant's Vision cards currently cause a crash when used by a non-Servant
                // character.
                nextHandCard.cardID == "Read" || nextHandCard.cardID == "Deadline"
                || nextHandCard.cardID == "ReturningBlade" || nextHandCard.cardID == "Snipe"
                || nextHandCard.cardID == "TimeTheft" || nextHandCard.cardID == "TrueSight");

        nextHandCard.freeToPlayOnce = true;
//        AbstractDungeon.player.limbo.addToTop(nextHandCard);

//        nextHandCard.target_x = Settings.WIDTH / 2;
//        nextHandCard.target_y = Settings.HEIGHT / 2;
//        nextHandCard.targetDrawScale = nextHandCard.targetDrawScale * 1.4f;
//        
        return nextHandCard;
    }
}