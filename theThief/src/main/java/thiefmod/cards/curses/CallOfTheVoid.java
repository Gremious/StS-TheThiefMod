package thiefmod.cards.curses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardIgnore;
import thiefmod.ThiefMod;
import thiefmod.actions.StealCardAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.powers.Unique.TheThiefThieveryPower;

@CardIgnore
public class CallOfTheVoid extends AbstractBackstabCard {
//TODO: Make this card.

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("CallOfTheVoid");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);
    public static final CardColor COLOR = CardColor.CURSE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:FlavorText");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 3;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final int BACKSTAB = 2;

    private static final String ADD_LOCATION = "Hand"; // If stolen card.
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public CallOfTheVoid() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.backstabNumber = this.baseBackstabNumber = BACKSTAB;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage * this.backstabNumber, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            AbstractDungeon.actionManager.addToBottom(new StealCardAction(
                    this.magicNumber, 1, ADD_RANDOM, ADD_LOCATION, ADD_UPGRADED));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new TheThiefThieveryPower(
                AbstractDungeon.player, AbstractDungeon.player, 3), 1));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                p, p, this.block));

    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            this.rawDescription = this.DESCRIPTION + this.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = this.DESCRIPTION + this.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CallOfTheVoid();
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
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}