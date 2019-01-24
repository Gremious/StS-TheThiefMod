package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Unique.ThiefCardTags;
import thiefmod.powers.Unique.StolenCodeButWaitPower;

public class StolenCode extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenCodePower");
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

    private static final int MAGIC = 1;
    // /STAT DECLARATION/


    public StolenCode() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = MAGIC;


        tags.add(ThiefCardTags.STOLEN);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StolenCodeButWaitPower(p, p, magicNumber), magicNumber));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            upgradeBaseCost(UPGRADE_COST);

            this.initializeDescription();
        }
    }
}