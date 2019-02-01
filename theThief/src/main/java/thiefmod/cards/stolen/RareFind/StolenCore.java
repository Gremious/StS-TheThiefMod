package thiefmod.cards.stolen.RareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
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

        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = ORB_SLOTS;


     /* Straight up just doesn't work. But maybe one day it will. And when that happens, I'll be waiting. And I will uncomment this code. And my rare cards will look cool.

        setBackgroundTexture("thiefmodAssets/images/512/special/blue_rare_skill_bg.png",
                "thiefmodAssets/images/1024/special/blue_rare_skill_bg.png");

        setOrbTexture("thiefmodAssets/images/512/card_thief_gray_orb.png",
                "thiefmodAssets/images/1024/card_thief_gray_orb.png");

    */

        setBannerTexture("thiefmodAssets/images/512/special/rare_skill_banner.png",
                "thiefmodAssets/images/1024/special/rare_skill_banner.png");

        tags.add(ThiefCardTags.STOLEN);
        exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.ROYAL));

        AbstractDungeon.actionManager.addToBottom(
                new IncreaseMaxOrbAction(backstabNumber));

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StolenCorePower(p, p, magicNumber), 1));

    }

    @Override
    public void triggerWhenDrawn() {

        AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));

    }

    @Override
    public void triggerWhenCopied() {

        AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber == 1) {
            rawDescription = DESCRIPTION;
        } else {
            rawDescription = UPGRADE_DESCRIPTION;
        }

        initializeDescription();
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