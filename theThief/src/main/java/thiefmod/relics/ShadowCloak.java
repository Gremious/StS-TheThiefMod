package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.powers.Common.ShadowstepPower;

public class ShadowCloak extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("ShadowCloak");
    public static final String IMG = "thiefmodAssets/images/relics/PocketChange.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/PocketChange.png";

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String TOOLTIP_STRINGS[] = uiStrings.TEXT;
    private AbstractCreature p = AbstractDungeon.player;
    private boolean usedThisTurn = false;
    private int count = 0;

    public ShadowCloak() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);

        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TOOLTIP_STRINGS[1],
                DESCRIPTIONS[1] + FontHelper.colorString(new ShadowMask().name, "y")
                        + DESCRIPTIONS[2] + FontHelper.colorString(new ShadowBoots().name, "y")
                        + DESCRIPTIONS[3]));

        initializeTips();
    }


    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!usedThisTurn) {
            if (AbstractDungeon.player.hasRelic(ShadowMask.ID) && AbstractDungeon.player.hasRelic(ShadowBoots.ID)) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(p, p, new ShadowstepPower(p, p, 1), 1));
            } else {
                if (count != 2) {
                    count++;
                } else {
                    AbstractDungeon.actionManager.addToBottom(
                            new ApplyPowerAction(p, p, new ShadowstepPower(p, p, 1), 1));
                }
            }
        }
    }


    public void atTurnStart() {
        count = 0;
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void atPreBattle() {
        count = 0;
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
