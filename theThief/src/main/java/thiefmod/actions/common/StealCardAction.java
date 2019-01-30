package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.DiscoverStolenCard;
import thiefmod.actions.Util.SuperCopyAction;
import thiefmod.actions.unique.StolenMegaphone;
import thiefmod.cards.stolen.*;
import thiefmod.cards.stolen.mystic.*;
import thiefmod.powers.Unique.FleetingGuiltPower;
import thiefmod.powers.Unique.IllGottenGainsPower;

import java.util.ArrayList;
import java.util.Objects;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static mysticmod.MysticMod.cantripsGroup;
import static thiefmod.ThiefMod.*;

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

            if (random) {
                cardsToAdd = getRandomStolenCards(amount, true);
                for (int i = 0; i < copies; i++) {
                    curseCounter();
                    addStolenCards();
                }
                cardsToAdd.clear();
            } else /*Discover*/ {
                if (amount > 0) {
                    curseCounter();
                    amount--;
                    AbstractDungeon.actionManager.addToBottom(new DiscoverStolenCard(getRandomStolenCards(3, false), 3, copies));
                    return; // Don't tickDuration, So that we can keep spamming the discover screen == amount of cards requested.
                }
                cardsToAdd.clear();
            }
        }

        this.tickDuration();
    }

    // Add the stolen cards to whatever location your heart desires.
    private void addStolenCards() {

        for (AbstractCard c : cardsToAdd) {
            logger.info("addStolenCards() adding card " + c + " to " + location);
            //    c.unhover();
            AbstractDungeon.actionManager.actions.add(new SuperCopyAction(c, "Exhaust", location));


            //TODO: Test whether or not having a full hand breaks this.
        }

        if (!Objects.equals(location, "Hand")
                && !Objects.equals(location, "Draw")
                && !Objects.equals(location, "Discard")) {
            logger.info("addStolenCards() didn't find ether hand, deck or discard.");
        }


    }

// ========================

    // Cardpool of non-upgraded cards.
    private static CardGroup stolenCards;

    static {
        stolenCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        stolenCards.addToTop(new StolenShieldGenerator());
        stolenCards.addToTop(new StolenCode());
        stolenCards.addToTop(new StolenMegaphone());
        stolenCards.addToTop(new StolenTV());
        stolenCards.addToTop(new StolenClaws());
        stolenCards.addToTop(new StolenOrb());
        stolenCards.addToTop(new StolenArmor());
        stolenCards.addToTop(new StolenWeapon());
        stolenCards.addToTop(new StolenAttitude());
        stolenCards.addToTop(new StolenMastery());
        stolenCards.addToTop(new StolenChaos());
        stolenCards.addToTop(new StolenMoves());
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

        // Rares:
        stolenCards.addToTop(new StolenShadow());
        stolenCards.addToTop(new StolenArsenal());
        stolenCards.addToTop(new StolenBlood());
        stolenCards.addToTop(new StolenCore());

        //---

        if (hasConspire) {
            ArrayList<AbstractCard> conspireCards = new ArrayList<>();

            conspireCards.add(CardLibrary.getCopy("conspire:Banana"));
            conspireCards.add(CardLibrary.getCopy("conspire:Treasure"));

            for (AbstractCard c : conspireCards) {
                if (c != null) {
                    c.name = "Stolen " + c.name;
                    stolenCards.addToTop(c);
                }
            }

        }

        //---

        if (hasHubris || hasInfiniteSpire || hasReplayTheSpire) {
            int roll = AbstractDungeon.relicRng.random(99);
            if (roll < 15) {
                ArrayList<AbstractCard> blackCards = new ArrayList<>();

                if (hasHubris) {

                    blackCards.add(CardLibrary.getCopy("hubris:Fate"));
                    blackCards.add(CardLibrary.getCopy("hubris:InfiniteBlow"));
                    blackCards.add(CardLibrary.getCopy("hubris:Rewind"));

                }
                if (hasInfiniteSpire) {

                    blackCards.add(CardLibrary.getCopy("infinitespire:Collect"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:DeathsTouch"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:Execution"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:FinalStrike"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:Fortify"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:FutureSight"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:Gouge"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:Menacing"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:NeuralNetwork"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:Punishment"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:TheBestDefense"));
                    blackCards.add(CardLibrary.getCopy("infinitespire:UltimateForm"));

                }
                if (hasReplayTheSpire) {

                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:Chaos Vortex"));
                    blackCards.add(CardLibrary.getCopy("Replay:Dark Deal"));
//                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:Dark Transmutation"));
//                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:Echo Chamber"));
                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:Echoes of Time"));
                    blackCards.add(CardLibrary.getCopy("Replay:Fractal Strike"));
                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:Haul"));
                    blackCards.add(CardLibrary.getCopy("ReplayTheSpireMod:??????????????????????"));

                }
                for (AbstractCard c : blackCards) {
                    if (c != null) {
                        stolenCards.addToTop(c);
                    }
                }
            }
        }

        //---

        if (hasMysticMod) {
            ArrayList<AbstractCard> customMysticCards = new ArrayList<>();
            ArrayList<AbstractCard> mysticCards = new ArrayList<>();

            customMysticCards.add(new stolenSpellScroll());
            customMysticCards.add(new stolenArteScroll());
            customMysticCards.add(new stolenMysticalSpellbook());
            customMysticCards.add(new stolenBookOfArte());
            customMysticCards.add(new stolenMagicTrinket());
            customMysticCards.add(new stolenBagOfMagicTrinkets());
            // Rare:
            customMysticCards.add(new stolenMysticalOrb());

            mysticCards.add(CardLibrary.getCopy("mysticmod:MagicMissile"));
            //   mysticCards.add(CardLibrary.getCopy("mysticmod:Spellstrike"));
            mysticCards.add(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));


            for (AbstractCard c : mysticCards) {
                if (c != null) {
                    c.name = "Stolen " + c.name;
                    stolenCards.addToTop(c);
                }
            }
            for (AbstractCard c : customMysticCards) {
                stolenCards.addToTop(c);

            }
        }

        //---

        // TODO: Test whether you really need this?
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

    // Generates random stolen cards
    private ArrayList<AbstractCard> getRandomStolenCards(int amount, boolean allowDuplicates) {

        ArrayList<AbstractCard> randomCards = new ArrayList<>();

        while (randomCards.size() < amount) {
            AbstractCard card = allStolenCards().getRandomCard(true);
            if (allowDuplicates || !randomCards.contains(card)) {

                randomCards.add(card);
            }
        }
        return randomCards;
    }


    private void curseCounter() {
        actionManager.addToBottom(new ApplyPowerAction(player, source,
                new FleetingGuiltPower(player, source, 1), 1));
    }

}