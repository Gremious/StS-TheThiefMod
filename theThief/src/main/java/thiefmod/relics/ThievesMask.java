package thiefmod.relics;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.shop.ShopScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.powers.Unique.TheThiefThieveryPower;

import java.util.ArrayList;

public class ThievesMask extends CustomRelic {
    // ID, images, stats.
    public static final String ID = thiefmod.ThiefMod.makeID("ThievesMask");
    public static final String IMG = "thiefmodAssets/images/relics/ThievesMask.png";
    public static final String OUTLINE ="thiefmodAssets/images/relics/outline/ThievesMask.png";
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    public ThievesMask() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.FLAT);
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
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TheThiefThieveryPower(AbstractDungeon.player, AbstractDungeon.player, false, 3), 1));

    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        logger.info("On room enter - triggered");
        if (room instanceof ShopRoom && AbstractDungeon.player.hasRelic(SmilingMask.ID)) {
            logger.info("This is a shop room.");
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

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new ThievesMask();
    }
}
