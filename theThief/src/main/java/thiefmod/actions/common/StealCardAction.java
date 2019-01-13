package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.Utils;
import thiefmod.cards.stolen.*;
import thiefmod.powers.Unique.IllGottenGainsPower;

import java.util.ArrayList;
import java.util.Objects;

public class StealCardAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private boolean random;
    private boolean cardOffset;
    private static boolean upgraded;
    private String location;
    private int copies;

    private static final float startingDuration = 0.5f;
    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();

    private static final boolean ALLOW_DUPLICATES = true;

    public StealCardAction(AbstractCreature source, int amount, int copies, boolean random, boolean cardOffset, String location, boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = startingDuration;
        this.amount = amount;
        this.random = random;
        this.cardOffset = cardOffset;
        this.upgraded = upgraded;
        this.location = location;
        this.copies = copies;
        this.source = source;
        logger.info("Initialize class");

    }


    @Override
    public void update() {
        if (this.duration == startingDuration) {

            //        if (p.hasPower(IllGottenGainsPower.POWER_ID)) {
//            this.upgraded = true;
//        }
            logger.info("Starting Duration");

            logger.info("Power Check Pass");
            if (random) { // Random card? If yes add a random card (without replacement).
                cardsToAdd = getRandomStolenCards(this.amount, ALLOW_DUPLICATES);
                for (int i = 0; i < this.copies; i++) {
                    addStolenCards();
                }
                cardsToAdd.clear();
            } else {
                if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                    for (int i = 0; i < this.copies; i++) {
                        AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
                        cardsToAdd.add(c);
                    }
                    AbstractDungeon.cardRewardScreen.codexCard = null;
                }
                if (amount > 0) {
                    this.amount--;
                    Utils.openCardRewardsScreen(getRandomStolenCards(3, false), true);
                    return; // don't tickDuration, so we can open the screen again
                } else {
                    addStolenCards();
                }
                cardsToAdd.clear();
            }
        }
        this.tickDuration();
    }

// ========================

    private void addStolenCards() {
        boolean randomSpot = true;

        for (AbstractCard c : cardsToAdd) {
            c.unhover();
            if (Objects.equals(this.location, new String("Hand"))) {
                logger.info("Found hand! Adding to hand.");

                // AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, (float) Settings.WIDTH / 2.0f,
                //       (float) Settings.HEIGHT / 2.0f));
                AbstractDungeon.actionManager.actions.add(new MakeTempCardInHandAction(c));

            } else if (Objects.equals(this.location, new String("Draw"))) {
                logger.info("Found Draw pile! Adding to Draw.");
                if (cardsToAdd.size() < 6) {
                    logger.info("Draw pile - cards are less than 6.");
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float) Settings.WIDTH / 2.0f,
                            (float) Settings.HEIGHT / 2.0f, randomSpot, cardOffset));
                } else {
                    logger.info("Draw pile - cards are more than 6");
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, randomSpot, false));
                }
            } else if (Objects.equals(this.location, new String("Discard"))) {
                logger.info("Found Discard! Adding to discard.");
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, (float) Settings.WIDTH / 2.0f,
                        (float) Settings.HEIGHT / 2.0f));
            } else {
                logger.info("Shuffle stolen cards: didn't find ether hand, deck or discard.");
            }

        }

    }

// ========================

    // Create a new card group of the cards. this is essentially your cardpool.
    private static CardGroup stolenCards;

    {
        logger.info("new card group - stolen cards");
        stolenCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        stolenCards.addToTop(new StolenGold());
        stolenCards.addToTop(new StolenMoves());
        stolenCards.addToTop(new StolenMomentum());
        stolenCards.addToTop(new StolenArtifice());
        stolenCards.addToTop(new StolenTechnique());
        stolenCards.addToTop(new StolenChange());
        stolenCards.addToTop(new StolenRitual());
        stolenCards.addToTop(new StolenBlades());
        stolenCards.addToTop(new StolenWire());
        stolenCards.addToTop(new StolenTrap());
        stolenCards.addToTop(new StolenToxins());

        stolenCards.sortAlphabetically(false);
    }

    // Card pool of upgraded cards.
    private static CardGroup stolenCardsUpgraded;

    {
        logger.info("new card group - stolen cards upgraded");
        stolenCardsUpgraded = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : stolenCards.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            stolenCardsUpgraded.addToTop(upgradedCopy);
        }
    }


    // CardGroup to be called that decides whether the stolen cards to add are upgraded or not.
    private CardGroup allStolenCards() {
        if (this.upgraded || this.source.hasPower(IllGottenGainsPower.POWER_ID)) {
            return stolenCardsUpgraded;
        } else {
            return stolenCards;
        }
    }

    // Method for getting random a random card.
    public AbstractCard getRandomStolenCard() {
        return allStolenCards().getRandomCard(false);
    }

    // If stolen cards are requested, this array list is the output.
    private ArrayList<AbstractCard> getRandomStolenCards(int amount, boolean allowDuplicates) {
        ArrayList<AbstractCard> cards = new ArrayList<>();

        while (cards.size() < amount) {
            int tries = 0;

            AbstractCard card = getRandomStolenCard();
            if (allowDuplicates || !cards.contains(card) || tries++ > 20) {
                cards.add(card);
            }
        }
        return cards;
    }
}
/*
    logger.info("get random stolen");
            if (allowDuplicates || !cards.contains(card) || tries++ > 20) {
                cards.add(card);
                logger.info(
                        "If loop started. This copies: " + this.copies + " .Cards is currently: " + cards.toString());
                for (int i = 0; i < this.copies; i++) {
                    logger.info("duplication loop started");
                    for (AbstractCard c : cards.subList(0, cards.size())) {
                        logger.info("for loop started. Duplicating " + c.toString());
                        AbstractCard duplicateCopy = c.makeCopy();
                        logger.info("duplicateCopy is " + duplicateCopy.toString());
                        cards.add(duplicateCopy);
                        logger.info("Cards is currently: " + cards.toString());

                    }
                }
            }
        }
 */