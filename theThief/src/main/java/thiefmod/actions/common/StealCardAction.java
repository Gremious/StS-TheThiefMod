package thiefmod.actions.common;

import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.actions.util.DiscoverCardAction;
import thiefmod.cards.stolen.*;
import thiefmod.cards.stolen.modSynergy.bard.StolenCredit;
import thiefmod.cards.stolen.modSynergy.bard.StolenEssence;
import thiefmod.cards.stolen.modSynergy.bard.StolenFlute;
import thiefmod.cards.stolen.modSynergy.bard.StolenSong;
import thiefmod.cards.stolen.modSynergy.bard.rareFind.StolenGreatHorn;
import thiefmod.cards.stolen.modSynergy.disciple.rareFind.StolenClock;
import thiefmod.cards.stolen.modSynergy.halation.rareFind.StolenMail;
import thiefmod.cards.stolen.modSynergy.mystic.rareFind.stolenMysticalOrb;
import thiefmod.cards.stolen.modSynergy.mystic.*;
import thiefmod.cards.stolen.modSynergy.theServant.rareFind.StolenKnives;
import thiefmod.cards.stolen.rareFind.StolenArsenal;
import thiefmod.cards.stolen.rareFind.StolenBlood;
import thiefmod.cards.stolen.rareFind.StolenCore;
import thiefmod.cards.stolen.rareFind.StolenShadow;
import thiefmod.patches.DiscoveryPatch;
import thiefmod.powers.Unique.FleetingGuiltPower;
import thiefmod.powers.Unique.IllGottenGainsPower;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static mysticmod.MysticMod.cantripsGroup;
import static thiefmod.ThiefMod.*;

public class StealCardAction extends AbstractGameAction implements CustomSavable<Integer> {
    public static final Logger logger = LogManager.getLogger(StealCardAction.class.getName());
    
    public boolean random;
    public boolean upgraded;
    public CardGroup location;
    public int copies;
    private int rollRare = AbstractDungeon.cardRandomRng.random(99);
    private CardGroup cardsToAdd = new CardGroup(StolenEnum.STOLEN_CARDS);
    public static int cardsStolenThisCombat = 0;
    
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
                for (int i = 0; i < amount; i++) {
                    curseCounter();
                    cardsStolenThisCombat++;
                }
                for (int i = 0; i < copies; i++) {
                    addStolenCards();
                    AbstractDungeon.actionManager.addToTop(new SFXAction("CARD_OBTAIN"));
                }
                cardsToAdd.clear();
            } else /*Discover*/ {
                if (amount > 0) {
                    amount--;
                    cardsToAdd = getRandomStolenCards(3, false);
                    for (AbstractCard c : cardsToAdd.group){
                        MakeStolenCardAction.makeStolenCard(c);
                    }
                    //AbstractDungeon.actionManager.addToBottom(new SFXAction("CARD_OBTAIN"));
                    AbstractDungeon.actionManager.addToBottom(new DiscoverCardAction(cardsToAdd, 3, upgraded, null, copies, true));
                    curseCounter();
                    cardsStolenThisCombat++;
                    return; // Don't tickDuration, so that we can keep spamming the discover screen == amount of cards requested.
                }
                cardsToAdd.clear();
            }
            
            if (cardsToAdd.isEmpty()) tickDuration();
        }
        tickDuration();
    }
    
    public static class StolenEnum {
        @SpireEnum
        public static CardGroup.CardGroupType STOLEN_CARDS;
    }
    
    // Add the stolen cards to whatever location your heart desires.
    private void addStolenCards() {
        for (AbstractCard c : cardsToAdd.group) {
            logger.info("addStolenCards() adding card " + c + " to " + location.toString());
            AbstractDungeon.actionManager.actions.add(new MakeStolenCardAction(c, location));
        }
    }
    
    // ========================
    
    // Cardpool of stolen cards.
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
        stolenCards.addToTop(new StolenAdvantage());
        
        //---
        
        if (hasConspire) {
            stolenCards.addToTop(CardLibrary.getCopy("conspire:Banana"));
            AbstractCard stolenTreasure = CardLibrary.getCopy("conspire:Treasure");
            stolenTreasure.cost = 0;
            stolenCards.addToTop(stolenTreasure);
        }
        
        //---
        
        if (hasMysticMod) {
            stolenCards.addToTop(new stolenSpellScroll());
            stolenCards.addToTop(new stolenMagicalWeapon());
            stolenCards.addToTop(new stolenMysticalSpellbook());
            stolenCards.addToTop(new stolenBoxOfWeapons());
            stolenCards.addToTop(new stolenMagicCantrip());
            stolenCards.addToTop(new stolenBagOfMagicCantrips());
            
            stolenCards.addToTop(CardLibrary.getCopy("mysticmod:MagicMissile"));
            stolenCards.addToTop(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));
        }
        
        //---
        
        if (hasHalation) {
            stolenCards.addToTop(CardLibrary.getCopy("halation:LetterOfAdmiration"));
            stolenCards.addToTop(CardLibrary.getCopy("halation:LetterOfRespect"));
            stolenCards.addToTop(CardLibrary.getCopy("halation:LetterOfLove"));
        }
        
        //---
        
        if (hasDisciple) {
            stolenCards.addToTop(CardLibrary.getCopy("Echoward"));
            stolenCards.addToTop(CardLibrary.getCopy("SlimeSpray"));
            stolenCards.addToTop(CardLibrary.getCopy("Accruing"));
        }
        
        //---
        
        if (hasServant) {
            stolenCards.addToTop(CardLibrary.getCopy("Contraction"));
            stolenCards.addToTop(CardLibrary.getCopy("Deadline"));
            stolenCards.addToTop(CardLibrary.getCopy("Misdirection"));
            stolenCards.addToTop(CardLibrary.getCopy("Moondial"));
        }
        
        //---
        
        if (hasBard) {
            stolenCards.addToTop(new StolenCredit());
            stolenCards.addToTop(new StolenEssence());
            stolenCards.addToTop(new StolenFlute());
            stolenCards.addToTop(new StolenSong());
            // Gamble?
        }
        
        //---
        
        stolenCards.shuffle();
    }
    
    // Card pool of upgraded stolen cards.
    private static CardGroup stolenCardsUpgraded;
    
    static {
        stolenCardsUpgraded = new CardGroup(StolenEnum.STOLEN_CARDS);
        for (AbstractCard c : stolenCards.group) {
            AbstractCard upgradedCopy = c.makeStatEquivalentCopy();
            upgradedCopy.upgrade();
            stolenCardsUpgraded.addToTop(upgradedCopy);
        }
    }
    
    // Card pool of rare find stolen cards
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
        if (hasServant) rareFinds.addToTop(new StolenKnives());
        if (hasBard) rareFinds.addToTop(new StolenGreatHorn());
    }
    
    // Card pool of upgraded rare finds
    private static CardGroup rareFindsUpgraded;
    
    static {
        rareFindsUpgraded = new CardGroup(StolenEnum.STOLEN_CARDS);
        for (AbstractCard c : rareFinds.group) {
            AbstractCard upgradedCopy = c.makeStatEquivalentCopy();
            upgradedCopy.upgrade();
            rareFindsUpgraded.addToTop(upgradedCopy);
        }
    }
    
    // A final group of the cards to return.
    private CardGroup allStolenCardsToUse() {
        logger.info("Cards Stolen This Combat: " + cardsStolenThisCombat);
        logger.info("Rare fnd roll: " + rollRare + " - " + (rollRare < 15));
        logger.info("Standard Checks: upgraded or has power? " + (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)));
        
        if (cardsStolenThisCombat == 19) {
            if (upgraded || AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)) {
                return rareFindsUpgraded;
            } else {
                return rareFinds;
            }
        } else if (cardsStolenThisCombat >= 20 && rollRare < 15) {
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
    private CardGroup getRandomStolenCards(int amount, boolean allowDuplicates) {
        ArrayList<AbstractCard> temp = new ArrayList<>();
        CardGroup randomCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        
        while (temp.size() < amount) { // Grab only the amount specified. While we don't have 'amount'...
            AbstractCard card = allStolenCardsToUse().getRandomCard(true); // Get a random upgraded/non-upgraded card.
            if (allowDuplicates || !DiscoveryPatch.cardUtil.containsByID(temp, card)) { // So long as we can get duplicates OR the card isn't a duplicate.
                temp.add(card); // And add it to the array
            }
        }
        
        for (AbstractCard c : temp) {
            randomCards.addToTop(MakeStolenCardAction.makeStolenCard(c));
        }
        
        return randomCards;
    }
    
    public static CardGroup getAllStolenCards(AbstractCard.CardRarity rarity, boolean upgraded, boolean all) {
        CardGroup allCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        if (all) {
            if (upgraded) {
                allCards.group.addAll(stolenCardsUpgraded.group);
                allCards.group.addAll(rareFindsUpgraded.group);
                return allCards;
            } else {
                allCards.group.addAll(stolenCards.group);
                allCards.group.addAll(rareFinds.group);
                return allCards;
            }
        } else if (rarity == AbstractCard.CardRarity.RARE) {
            if (upgraded) {
                allCards.group.addAll(rareFindsUpgraded.group);
                return allCards;
            } else {
                allCards.group.addAll(rareFinds.group);
                return allCards;
            }
        } else {
            if (upgraded) {
                allCards.group.addAll(stolenCardsUpgraded.group);
                return allCards;
            } else {
                allCards.group.addAll(stolenCards.group);
                return allCards;
            }
        }
    }
    
    private void curseCounter() {
        actionManager.addToBottom(new ApplyPowerAction(player, source,
                new FleetingGuiltPower(player, source, 1), 1));
    }
    
    @Override
    public Integer onSave() {
        return cardsStolenThisCombat;
    }
    
    @Override
    public void onLoad(Integer cardNum) {
        cardsStolenThisCombat = cardNum;
    }
}