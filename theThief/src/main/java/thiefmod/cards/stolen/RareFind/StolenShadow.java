package thiefmod.cards.stolen.RareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.StolenShadowAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

public class StolenShadow extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenShadow");
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
    // /STAT DECLARATION/


    public StolenShadow() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = MAGIC;


        tags.add(ThiefCardTags.STOLEN);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.BLACK));
        AbstractDungeon.actionManager.addToBottom(new StolenShadowAction(p, magicNumber));

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

//          rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}