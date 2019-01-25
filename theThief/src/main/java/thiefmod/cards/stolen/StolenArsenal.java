package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Unique.ThiefCardTags;
import thiefmod.powers.Unique.SimilarSkillsPower;

public class StolenArsenal extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenArsenal");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int DAMAGE = ;
    private static final int UPGRADE_PLUS_DAMAGE = ;

    private static final int BLOCK = ;
    private static final int UPGRADE_PLUS_BLOCK = ;

    private static final int MAGIC = ;
    private static final int UPGRADED_PLUS_MAGIC = ;

    private static final int BACKSTAB = ;
    private static final int UPGRADED_PLUS_BACKSTAB = ;

    // /STAT DECLARATION/


    public StolenArsenal() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseDamage = DAMAGE;
        this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = MAGIC;


        tags.add(ThiefCardTags.STOLEN);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new SimilarSkillsPower(p, this.magicNumber), this.magicNumber));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            upgradeBaseCost(UPGRADE_COST);
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);

//          rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}