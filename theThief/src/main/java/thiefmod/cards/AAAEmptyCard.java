package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thiefmod.CardIgnore;
import thiefmod.actions.common.StealCardAction;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.ShadowstepPower;

import java.util.ArrayList;
import java.util.List;
@Deprecated
@CardIgnore
public class AAAEmptyCard extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION 

    public static final String ID = thiefmod.ThiefMod.makeID("AAAEmptyCard");
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 3;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final int BACKSTAB = 2;
    private static final int UPGRADED_PLUS_BACKSTAB = 1;

    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public AAAEmptyCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        ExhaustiveVariable.setBaseValue(this, 2);

        FleetingField.fleeting.set(this, true);

        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        baseBlock = BLOCK;
        backstabNumber = baseBackstabNumber = BACKSTAB;


        tags.add(ThiefCardTags.BACKSTAB);
        tags.add(ThiefCardTags.SHADOWSTEP);
        tags.add(ThiefCardTags.STEALING);
        tags.add(ThiefCardTags.STOLEN);
        /*
            modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.GREEN) // Sets color of any following cards to red
                .addOption("Fetch a card from your draw pile.", CardTarget.NONE)
                .setColor(CardColor.COLORLESS) // Sets color of any following cards to green
                .addOption("Fetch a card from your discard pile.", CardTarget.NONE)
                .setColor(CardColor.CURSE) // Sets color of any following cards to colorless
                .addOption("Fetch a card from your exhaust pile.", CardTarget.NONE)
                .create();
         */
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1) {
            action(new DamageAction(
                    m, new DamageInfo(p, damage * backstabNumber, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            action(new StealCardAction(
                    magicNumber, 1, ADD_RANDOM, AbstractDungeon.player.hand, ADD_UPGRADED));
        }

        action(new ApplyPowerAction(
                p, p, new ShadowstepPower(
                p, p, magicNumber), magicNumber));

        action(new GainBlockAction(
                p, p, block));

        while (backstabNumber-- != 0) {
            action(
                    new MakeTempCardInDrawPileAction(new VoidCard(), backstabNumber, true, true, false));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            action(new ApplyPowerAction(mo, p, new VulnerablePower(
                    mo, magicNumber, false), magicNumber));
        }

        backstabNumber = baseBackstabNumber;
        action(new RemoveSpecificPowerAction(p, p, StrengthPower.POWER_ID));

    }

    /*

    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        action(
                new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));
        return true;
    }

    */

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber >= 2) {
            rawDescription = UPGRADE_DESCRIPTION;
        } else {
            rawDescription = DESCRIPTION;
        }

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            rawDescription += EXTENDED_DESCRIPTION[1];
        } else {
            rawDescription += EXTENDED_DESCRIPTION[2];
        }
        initializeDescription();
    }

    /*
        @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        switch (i) {
            case 0:
                 AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.discardPile, magicNumber));
                break;
            case 2:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, magicNumber));
                break;
            default:
                return;
        }
    }
     */
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}