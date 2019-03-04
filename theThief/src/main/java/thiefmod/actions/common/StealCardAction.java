package thiefmod.actions.common;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
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
import thiefmod.cards.stolen.modSynergy.disciple.rareFind.StolenClock;
import thiefmod.cards.stolen.modSynergy.halation.rareFind.StolenMail;
import thiefmod.cards.stolen.modSynergy.mystic.*;
import thiefmod.cards.stolen.modSynergy.mystic.rareFind.stolenMysticalOrb;
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
    private static int rollBlack = AbstractDungeon.cardRandomRng.random(99);

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
                    return; // Don't tickDuration, So that we can keep spamming the discover screen == amount of archetypes requested.
                }
                cardsToAdd.clear();
            }
        }
        tickDuration();
    }

    // Add the stolen archetypes to whatever location your heart desires.
    private void addStolenCards() {
        for (AbstractCard c : cardsToAdd) {
            logger.info("addStolenCards() adding card " + c + " to " + location.toString());
            AbstractDungeon.actionManager.actions.add(new MakeSuperCopyAction(c, KEYWORD_STRINGS[0], location));

        }
    }

// ========================

    public static class StolenEnum {
        @SpireEnum
        public static CardGroup.CardGroupType STOLEN_CARDS;
    }

    // Cardpool of stolen archetypes.
    private static CardGroup stolenCards;

    static {
        stolenCards = new CardGroup(StolenEnum.STOLEN_CARDS);

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

        // Thief/General
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

        if (hasDisciple) {
            ArrayList<AbstractCard> discipleCards = new ArrayList<>();

            discipleCards.add(CardLibrary.getCopy("Echoward"));
            discipleCards.add(CardLibrary.getCopy("SlimeSpray"));
            discipleCards.add(CardLibrary.getCopy("Accruing"));

            for (AbstractCard c : discipleCards) {
                if (c != null) {

                    if (!c.cardID.equals("Accruing")) {
                        c.name = STEAL_STRINGS[5] + c.name;
                    } else {
                        c.name = STEAL_STRINGS[5] + STEAL_STRINGS[6];
                    }

                    stolenCards.addToTop(c);
                }
            }
        }
        if (hasServant) {
            ArrayList<AbstractCard> servantCards = new ArrayList<>();

            servantCards.add(CardLibrary.getCopy("Contraction"));
            servantCards.add(CardLibrary.getCopy("Deadline"));
            servantCards.add(CardLibrary.getCopy("Misdirection"));
            servantCards.add(CardLibrary.getCopy("Moondial"));

            for (AbstractCard c : servantCards) {
                if (c != null) {
                    if (!c.cardID.equals("Misdirection")) {
                        c.name = STEAL_STRINGS[5] + c.name;
                    }
                    stolenCards.addToTop(c);
                }
            }
        }
        //---
    }

    // Card pool of upgraded stolen archetypes.
    private static CardGroup stolenCardsUpgraded;

    static {
        stolenCardsUpgraded = new CardGroup(StolenEnum.STOLEN_CARDS);
        for (AbstractCard c : stolenCards.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            stolenCardsUpgraded.addToTop(upgradedCopy);
        }
    }


    // Card pool of rare find stolen archetypes
    private static CardGroup rareFinds;

    static {
        rareFinds = new CardGroup(StolenEnum.STOLEN_CARDS);

        rareFinds.addToTop(new StolenArsenal());
        // Silent Rare Card Idea: Add a copy of every silent unplayable discard card to your hand/deck/draw.
        // At the start of each turn, discard and redraw your hand.
        rareFinds.addToTop(new StolenBlood());
        rareFinds.addToTop(new StolenCore());
        rareFinds.addToTop(new StolenShadow());

        if (hasMysticMod) rareFinds.addToTop(new stolenMysticalOrb());
        if (hasHalation) rareFinds.addToTop(new StolenMail());
        if (hasDisciple) rareFinds.addToTop(new StolenClock());
        if (hasServant) rareFinds.addToTop(new StolenClock());
    }

    // Card pool of upgraded rare finds
    private static CardGroup rareFindsUpgraded;

    static {
        rareFindsUpgraded = new CardGroup(StolenEnum.STOLEN_CARDS);
        for (AbstractCard c : rareFinds.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            rareFindsUpgraded.addToTop(upgradedCopy);
        }
    }

    private static CardGroup blackCards;

    static {
        blackCards = new CardGroup(StolenEnum.STOLEN_CARDS);

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

                        if (!c.cardID.equals("infinitespire:Collect")
                                && !c.cardID.equals("infinitespire:Fortify")
                                && !c.cardID.equals("infinitespire:Gouge")
                                && !c.cardID.equals("infinitespire:Menacing")
                                && !c.cardID.equals("infinitespire:TheBestDefense")
                                && !c.cardID.equals("ReplayTheSpireMod:Echoes of Time")) {
                            c.name = STEAL_STRINGS[5] + c.name;
                        }

                        if (c.cardID.equals("infinitespire:Collect")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[7];
                        } else if (c.cardID.equals("infinitespire:Fortify")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[8];
                        } else if (c.cardID.equals("infinitespire:Gouge")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[9];
                        } else if (c.cardID.equals("infinitespire:Menacing")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[10];
                        } else if (c.cardID.equals("infinitespire:TheBestDefense")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[11];
                        } else if (c.cardID.equals("ReplayTheSpireMod:Echoes of Time")) {
                            c.name = STEAL_STRINGS[5] + STEAL_STRINGS[12];
                        }

                        stolenCards.addToTop(c);
                    }
                }
            }
        }
    }

    private static CardGroup blackCardsUpgraded;

    static {
        blackCardsUpgraded = new CardGroup(StolenEnum.STOLEN_CARDS);
        for (AbstractCard c : blackCards.group) {
            AbstractCard upgradedCopy = c.makeCopy();
            upgradedCopy.upgrade();
            blackCardsUpgraded.addToTop(upgradedCopy);
        }
    }

    // ========================

    // A final group of the archetypes to return.
    private CardGroup allStolenCards() {
        if (rollRare < 15) {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return rareFindsUpgraded;
            } else {
                return rareFinds;
            }
        } else if ((hasHubris || hasInfiniteSpire || hasReplayTheSpire) && rollBlack < 8) {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return blackCardsUpgraded;
            } else {
                return blackCards;
            }
        } else {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return stolenCardsUpgraded;
            } else {
                return stolenCards;
            }
        }
    }

    // Grab random stolen archetypes
    private ArrayList<AbstractCard> getRandomStolenCards(int amount, boolean allowDuplicates) {
        ArrayList<AbstractCard> randomCards = new ArrayList<>();

        while (randomCards.size() < amount) { // Grab only the amount specified. While we don't have 'amount'...

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