package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.DiscoverAndExhaustCard;
import thiefmod.actions.Util.MakeSuperCopyAction;
import thiefmod.cards.stolen.*;
import thiefmod.cards.stolen.halation.rareFind.StolenMail;
import thiefmod.cards.stolen.mystic.rareFind.stolenMysticalOrb;
import thiefmod.cards.stolen.mystic.*;
import thiefmod.cards.stolen.rareFind.StolenArsenal;
import thiefmod.cards.stolen.rareFind.StolenBlood;
import thiefmod.cards.stolen.rareFind.StolenCore;
import thiefmod.cards.stolen.rareFind.StolenShadow;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Unique.FleetingGuiltPower;
import thiefmod.powers.Unique.IllGottenGainsPower;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static mysticmod.MysticMod.cantripsGroup;
import static thiefmod.ThiefMod.*;

public class StealCardAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    public static final UIStrings uiKeywordStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final UIStrings uiStealStrings = CardCrawlGame.languagePack.getUIString("theThief:StealCardUtil");
    public static final String KEYWORD_STRINGS[] = uiKeywordStrings.TEXT;
    public static final String STEAL_STRINGS[] = uiStealStrings.TEXT;

    public boolean random;
    public boolean upgraded;
    public CardGroup location;
    public int copies;

    private static int rollRare = AbstractDungeon.cardRandomRng.random(99);

    private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();

    public StealCardAction(int amount, int copies, boolean random, CardGroup location, boolean upgraded) {
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.random = random;
        this.upgraded = upgraded;
        this.location = location;
        this.copies = copies;
    }


    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {

            if (random) {
                cardsToAdd = getRandomStolenCards(amount, true);

                for (int i = 0; i < amount; i++) curseCounter();

                for (int i = 0; i < copies; i++) addStolenCards();

                cardsToAdd.clear();
            } else /*Discover*/ {
                if (amount > 0) {
                    amount--;

                    AbstractDungeon.actionManager.addToBottom(
                            new DiscoverAndExhaustCard(getRandomStolenCards(3, false), 3, copies));

                    curseCounter();
                    return; // Don't tickDuration, So that we can keep spamming the discover screen == amount of cards requested.
                }
                cardsToAdd.clear();
            }
        }
        tickDuration();
    }

    // Add the stolen cards to whatever location your heart desires.
    private void addStolenCards() {

        for (AbstractCard c : cardsToAdd) {
            logger.info("addStolenCards() adding card " + c + " to " + location.toString());
            //    c.unhover();

            AbstractDungeon.actionManager.actions.add(new MakeSuperCopyAction(c, KEYWORD_STRINGS[0], location));


            //TODO: Test whether or not having a full hand breaks this.
        }
    }

// ========================

    // Cardpool of stolen cards.
    private static CardGroup stolenCards;

    static {
        stolenCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        int rollBlack = AbstractDungeon.cardRandomRng.random(99);

        //--

        // Silent:
        stolenCards.addToTop(new StolenBlades());
        stolenCards.addToTop(new StolenWire());
        stolenCards.addToTop(new StolenTrap());
        stolenCards.addToTop(new StolenToxins());
        stolenCards.addToTop(new StolenMoves());
        stolenCards.addToTop(new StolenMomentum());

        // Ironclad:
        stolenCards.addToTop(new StolenArmor());
        stolenCards.addToTop(new StolenWeapon());
        stolenCards.addToTop(new StolenAttitude());
        stolenCards.addToTop(new StolenMastery());
        stolenCards.addToTop(new StolenChaos());

        //Defect:
        stolenCards.addToTop(new StolenShieldGenerator());
        stolenCards.addToTop(new StolenCode());
        stolenCards.addToTop(new StolenMegaphone());
        stolenCards.addToTop(new StolenTV());
        stolenCards.addToTop(new StolenClaws());
        stolenCards.addToTop(new StolenOrb());

        // Thief
        stolenCards.addToTop(new StolenGold());
        stolenCards.addToTop(new StolenCandy());
        stolenCards.addToTop(new StolenArtifice());
        stolenCards.addToTop(new StolenTechnique());
        stolenCards.addToTop(new StolenChange());
        stolenCards.addToTop(new StolenRitual());

        //---

        if (hasConspire) {
            ArrayList<AbstractCard> conspireCards = new ArrayList<>();

            conspireCards.add(CardLibrary.getCopy("conspire:Banana"));
            conspireCards.add(CardLibrary.getCopy("conspire:Treasure"));

            for (AbstractCard c : conspireCards) {
                if (c != null) {
                    c.name = STEAL_STRINGS[5] + c.name;
                    stolenCards.addToTop(c);
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
            customMysticCards.add(new stolenMagicCantrip());
            customMysticCards.add(new stolenBagOfMagicCantrips());
            mysticCards.add(CardLibrary.getCopy("mysticmod:MagicMissile"));
            mysticCards.add(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));

            for (AbstractCard c : mysticCards) {
                if (c != null) {
                    c.name = STEAL_STRINGS[5] + c.name;
                    stolenCards.addToTop(c);
                }
            }
            for (AbstractCard c : customMysticCards) {
                stolenCards.addToTop(c);
            }
        }

        //---

        if (hasHalation) {
            ArrayList<AbstractCard> halationCards = new ArrayList<>();

            halationCards.add(CardLibrary.getCopy("halation:LetterOfAdmiration"));
            halationCards.add(CardLibrary.getCopy("halation:LetterOfRespect"));
            halationCards.add(CardLibrary.getCopy("halation:LetterOfLove"));

            for (AbstractCard c : halationCards) {
                if (c != null) {
                    c.name = STEAL_STRINGS[5] + c.name;
                    stolenCards.addToTop(c);
                }
            }
        }

        //---

        if (hasHubris || hasInfiniteSpire || hasReplayTheSpire) {
            if (rollBlack < 25) {
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
    }

    // Card pool of upgraded stolen cards.
    private static CardGroup stolenCardsUpgraded;
    static {
        stolenCardsUpgraded = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : stolenCards.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            stolenCardsUpgraded.addToTop(upgradedCopy);
        }
    }


    // Card pool of rare find stolen cards
    private static CardGroup rareFinds;
    static {
        rareFinds = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        rareFinds.addToTop(new StolenArsenal());
        // Silent Rare Card Idea: Add a copy of every silent unplayable discard card to your hand/deck/draw.
        // At the start of each turn, discard and redraw your hand.
        rareFinds.addToTop(new StolenBlood());
        rareFinds.addToTop(new StolenCore());
        rareFinds.addToTop(new StolenShadow());

        if (hasMysticMod) rareFinds.addToTop(new stolenMysticalOrb());
        if (hasHalation) rareFinds.addToTop(new StolenMail());
    }


    // Card pool of rare find stolen cards
    private static CardGroup rareFindsUpgraded;
    static {
        rareFindsUpgraded = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : rareFinds.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            rareFindsUpgraded.addToTop(upgradedCopy);
        }
    }

    // CardGroup to be called that decides whether the stolen cards to add are upgraded or not.
    private CardGroup allStolenCards() {
        if (rollRare < 25) {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return rareFindsUpgraded;
            } else {
                return rareFinds;
            }
        } else {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return stolenCardsUpgraded;
            } else {
                return stolenCards;
            }
        }
    }

    // Grab random stolen cards
    private ArrayList<AbstractCard> getRandomStolenCards(int amount, boolean allowDuplicates) {

        ArrayList<AbstractCard> randomCards = new ArrayList<>();

        while (randomCards.size() < amount) { // Grab only the amount specified. While we don't have 'amount'
            AbstractCard card = allStolenCards().getRandomCard(true); // Get a random upgraded/non-upgraded card.
            if (allowDuplicates || !randomCards.contains(card)) { // So long as we can get duplicates OR the card isn't a duplicate.
                if (!card.hasTag(ThiefCardTags.STOLEN)) {
                    card.tags.add(ThiefCardTags.STOLEN); // Add the stolen card tag
                }
                randomCards.add(card); // And add it to the array
            }
        }
        return randomCards;
    }


    private void curseCounter() {

        actionManager.addToBottom(new ApplyPowerAction(player, source,
                new FleetingGuiltPower(player, source, 1), 1));

    }
}