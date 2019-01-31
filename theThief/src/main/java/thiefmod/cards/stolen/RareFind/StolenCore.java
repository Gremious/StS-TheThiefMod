package thiefmod.cards.stolen.RareFind;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.ThiefCardTags;
import thiefmod.powers.Unique.StolenCorePower;

import java.util.ArrayList;
import java.util.List;

public class StolenCore extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenCore");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;


    private static final int MAGIC = 1;
    private static final int ORB_SLOTS = 3;

    // /STAT DECLARATION/


    public StolenCore() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.backstabNumber = this.baseBackstabNumber = ORB_SLOTS;


        tags.add(ThiefCardTags.STOLEN);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new IncreaseMaxOrbAction(backstabNumber));

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StolenCorePower(p, p, magicNumber), 1));

    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber > 1) {
            rawDescription = DESCRIPTION;
        } else {
            rawDescription = UPGRADE_DESCRIPTION;
        }

        this.initializeDescription();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            upgradeBaseCost(UPGRADE_COST);

            initializeDescription();
        }
    }
}