package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.actions.common.StealCardAction;
import thiefmod.powers.Unique.IllGottenGainsPower;

public class StickyGloves extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("StickyGloves");
    public static final String IMG = "thiefmodAssets/images/relics/StickyGloves.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/StickyGloves.png";


    public StickyGloves() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }


    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new StealCardAction(1, 1, true, AbstractDungeon.player.hand, AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)));
    }

    @Override
    public int getPrice() {
        return 120;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
