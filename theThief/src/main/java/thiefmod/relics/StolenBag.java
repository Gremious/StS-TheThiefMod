package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.patches.character.AbstractCardEnum;

public class StolenBag extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("StolenBag");
    public static final String IMG = "theThiefAssets/images/relics/StolenBag.png";
    public static final String OUTLINE = "theThiefAssets/images/relics/outline/StolenBag.png";

    private boolean usedThisCombat = false;

    int count = 0;

    public StolenBag() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction useCardAction) {
        if (c.color != AbstractCardEnum.THIEF_GRAY) {
            count++;
            if (count >= 2 && !usedThisCombat) {
                count = 0;

                AbstractDungeon.actionManager.addToBottom(new FastDrawCardAction(AbstractDungeon.player, 2));
                usedThisCombat = true;
            }
        }
    }

    @Override
    public void atTurnStart() {
        count = 0;
        if (!usedThisCombat) {
            beginLongPulse();
        }
    }

    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        count = 0;
        beginLongPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
