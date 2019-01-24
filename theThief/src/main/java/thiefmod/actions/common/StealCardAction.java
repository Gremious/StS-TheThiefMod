package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
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
    private boolean upgraded;
    private String location;
    private int copies;

    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();

    public StealCardAction(int amount, int copies, boolean random, String location, boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.random = random;
        this.upgraded = upgraded;
        this.location = location;
        this.copies = copies;
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (random) { // Random card? If yes add a random card (without replacement).
                cardsToAdd = getRandomStolenCards(amount, true);
                for (int i = 0; i < copies; i++) {
                    addStolenCards();
                }
                cardsToAdd.clear();
            } else {
                if (AbstractDungeon.cardRewardScreen.codexCard != null) {
                    for (int i = 0; i < copies; i++) {
                        AbstractCard c = AbstractDungeon.cardRewardScreen.codexCard.makeStatEquivalentCopy();
                        cardsToAdd.add(c);
                    }
                    AbstractDungeon.cardRewardScreen.codexCard = null;
                }
                if (amount > 0) {
                    amount--;
                    Utils.openCardRewardsScreen(getRandomStolenCards(3, false), true);
                    return; // Don't tickDuration, So that we can keep spamming the discover screen == amount of cards requested.
                } else {
                    addStolenCards();
                }
                cardsToAdd.clear();
            }
        }
        this.tickDuration();
    }

// ========================

    // Create a new card group of the cards. this is essentially your cardpool.
    private static CardGroup stolenCards;

    {
        stolenCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        stolenCards.addToTop(new StolenGold());
        stolenCards.addToTop(new StolenCandy());
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
        stolenCardsUpgraded = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : stolenCards.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            stolenCardsUpgraded.addToTop(upgradedCopy);
        }
    }


    // CardGroup to be called that decides whether the stolen cards to add are upgraded or not.
    private CardGroup allStolenCards() {
        if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
            return stolenCardsUpgraded;
        } else {
            return stolenCards;
        }
    }

    // If stolen cards are requested, this array list is the output.
    private ArrayList<AbstractCard> getRandomStolenCards(int amount, boolean allowDuplicates) {

        ArrayList<AbstractCard> cards = new ArrayList<>();

        while (cards.size() < amount) {
            int tries = 0;
            AbstractCard card = allStolenCards().getRandomCard(true);
            if (allowDuplicates || !cards.contains(card) || tries++ > 20) {
                cards.add(card);
            }
        }
        return cards;
    }

    // ========================
    private void addStolenCards() {

        for (AbstractCard c : cardsToAdd) {
            c.unhover();
            if (Objects.equals(this.location, "Hand")) { //TODO: Test whether or not having a full hand breaks this.
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.actions.add(new MakeTempCardInHandAction(c, 1));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                }
            } else if (Objects.equals(this.location, "Draw")) {
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.actions.add(new MakeTempCardInDrawPileAction(c, 1, false, true));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
                }
            } else if (Objects.equals(this.location, "Discard")) {
                if (Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.actions.add(new MakeTempCardInDiscardAction(c, 1));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            } else {
                logger.info("addStolenCards() didn't find ether hand, deck or discard.");
            }

        }

    }

    //, (float) Settings.WIDTH / 2.0f, (float) Settings.HEIGHT / 2.0f - See if ShowCardAndAddToDiscardEffect looks find without noting this X and Y
// ========================
}