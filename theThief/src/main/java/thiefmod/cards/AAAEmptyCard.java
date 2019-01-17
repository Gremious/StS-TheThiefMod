package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thiefmod.ThiefMod;
import thiefmod.actions.common.StealCardAction;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.powers.Common.ShadowstepPower;

import java.util.ArrayList;
import java.util.List;

public class AAAEmptyCard extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION 

    public static final String ID = thiefmod.ThiefMod.makeID("AAAEmptyCard");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

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

    private static final String ADD_LOCATION = "Hand"; // If stolen card.
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public AAAEmptyCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        ExhaustiveVariable.setBaseValue(this, 2);

        FleetingField.fleeting.set(this, true);
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.baseBlock = BLOCK;
        this.backstabNumber = this.baseBackstabNumber = BACKSTAB;

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
            AbstractDungeon.actionManager.addToBottom(new DamageAction(
                    m, new DamageInfo(p, this.damage * this.backstabNumber, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            AbstractDungeon.actionManager.addToBottom(new StealCardAction(
                    p, this.magicNumber, 1, ADD_RANDOM, true, ADD_LOCATION, ADD_UPGRADED));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new ShadowstepPower(
                p, p, this.magicNumber), this.magicNumber));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                p, p, this.block));

        while (this.backstabNumber-- != 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new MakeTempCardInDrawPileAction(new VoidCard(), this.backstabNumber, true, true, false));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(
                    mo, this.magicNumber, false), this.magicNumber));
        }

        this.backstabNumber = this.baseBackstabNumber;
    }

    /*

    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(new VoidCard(), this.magicNumber, true, true, false));
        return true;
    }

    */

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (this.magicNumber >= 2) {
            this.rawDescription = UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION;
        }

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            this.rawDescription += this.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription += this.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    /*
        @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        switch (i) {
            case 0:
                 AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, this.magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.discardPile, this.magicNumber));
                break;
            case 2:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, this.magicNumber));
                break;
            default:
                return;
        }
    }
     */
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo("Flavor Text", EXTENDED_DESCRIPTION[0]));
        // tips.addAll(modal.generateTooltips());
        return tips;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new AAAEmptyCard();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}