package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.actions.unique.LoadedDiceAction;

public class LoadedDice extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("LoadedDice");
    public static final String IMG = "thiefmodAssets/images/relics/LoadedDice.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/LoadedDice.png";


    private boolean activated = false;

    public LoadedDice() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }


    public void atBattleStartPreDraw() {
        this.activated = false;
    }

    public void atTurnStartPostDraw() {
        if (!this.activated) {
            this.activated = true;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            AbstractDungeon.actionManager.addToBottom(new LoadedDiceAction(AbstractDungeon.player));
        }

    }

    @Override
    public int getPrice() {
        return 270;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
