package thiefmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.patches.AbstractCardEnum;
import thiefmod.powers.BackstabPower;
import thiefmod.powers.ShadowFormPower;

public class ShadowForm extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = thiefmod.ThiefMod.makeID("ShadowForm");
    public static final String IMG = ThiefMod.makePath(ThiefMod.SHADOW_FORM);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int MAGIC = 1;
    private static final int BACKSTAB = 2;

    // /STAT DECLARATION/

    public ShadowForm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.rawDescription = this.DESCRIPTION + this.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();

        this.isEthereal = this.upgraded;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.baseBackstabNumber = this.backstabNumber = BACKSTAB;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1 || p.hasPower(BackstabPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ShadowFormPower(p, this.magicNumber * this.backstabNumber),
                    this.magicNumber * this.backstabNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ShadowFormPower(p, this.magicNumber), this.magicNumber));
        }
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
        return new ShadowForm();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}