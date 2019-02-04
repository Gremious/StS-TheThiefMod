package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.powers.Unique.TheThiefThieveryPower;

public class ThievesMask extends CustomRelic {
    // ID, images, stats.
    public static final String ID = thiefmod.ThiefMod.makeID("ThievesMask");
    public static final String IMG = "thiefmodAssets/images/relics/ThievesMask.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/ThievesMask.png";
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    public ThievesMask() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.FLAT);
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip("Synergy",
                DESCRIPTIONS[1] + FontHelper.colorString(new SmilingMask().name, "y") + DESCRIPTIONS[2]));
        initializeTips();
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TheThiefThieveryPower(AbstractDungeon.player, AbstractDungeon.player, 3), 3));

    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        logger.info("On room enter - triggered");
        if (room instanceof ShopRoom && AbstractDungeon.player.hasRelic(SmilingMask.ID)) {
            logger.info("This is a shop room and you have smiling mask.");
            this.flash();
            this.pulse = true;
        } else {
            this.pulse = false;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + new SmilingMask().name + ".";
    }

}
