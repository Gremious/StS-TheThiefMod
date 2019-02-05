package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
@Deprecated
public class RelicTemplate extends CustomRelic implements ClickableRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("RelicTemplate");
    public static final String IMG = "thiefmodAssets/images/relics/RelicTemplate.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/RelicTemplate.png";

    private boolean usedThisTurn = false;

    public RelicTemplate() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }


    @Override
    public void onRightClick() {
        if (!isObtained || usedThisTurn) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisTurn = true;
            flash();
            stopPulse();

            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
        }
    }

    public void atTurnStart() {
        usedThisTurn = false;
        beginLongPulse();
    }

    @Override
    public void atPreBattle() {
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
