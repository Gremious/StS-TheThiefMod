package thiefmod.cards.stolen.RareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

public class StolenBlood extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenBlood");
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


    private static final int MAGIC = 8;

    // /STAT DECLARATION/


    public StolenBlood() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        setBackgroundTexture("thiefmodAssets/images/512/special/red_rare_skill_bg.png",
                "thiefmodAssets/images/1024/special/red_rare_skill_bg.png");

        setOrbTexture("thiefmodAssets/images/512/card_thief_gray_orb.png",
                "thiefmodAssets/images/1024/card_thief_gray_orb.png");

        setBannerTexture("thiefmodAssets/images/512/special/red_rare_skill_banner.png",
                "thiefmodAssets/images/1024/special/red_rare_skill_banner.png");

        this.magicNumber = this.baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.GOLD));

        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));

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
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
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