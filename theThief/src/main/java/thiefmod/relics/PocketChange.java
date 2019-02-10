package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.MawBank;
import com.megacrit.cardcrawl.relics.SmilingMask;
import thiefmod.powers.Common.RefundCardCostPower;

public class PocketChange extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("PocketChange");
    public static final String IMG = "thiefmodAssets/images/relics/PocketChange.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/PocketChange.png";

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String TOOLTIP_STRINGS[] = uiStrings.TEXT;
    public PocketChange() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TOOLTIP_STRINGS[1],
                DESCRIPTIONS[1] + FontHelper.colorString(new SmilingMask().name, "y") + DESCRIPTIONS[2]));
        initializeTips();
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new RefundCardCostPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + new MawBank().name + ".";
    }

}
