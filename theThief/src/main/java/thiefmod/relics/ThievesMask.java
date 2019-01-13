package thiefmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import thiefmod.ThiefMod;
import thiefmod.powers.Unique.TheThiefThieveryPower;

public class ThievesMask extends CustomRelic {
    // ID, images, stats.
    public static final String ID = thiefmod.ThiefMod.makeID("ThievesMask");
    public static final String IMG = ThiefMod.makePath(ThiefMod.PLACEHOLDER_RELIC);
    public static final String OUTLINE = ThiefMod.makePath(ThiefMod.PLACEHOLDER_RELIC_OUTLINE);

    public ThievesMask() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TheThiefThieveryPower(AbstractDungeon.player, AbstractDungeon.player, false, 3), 1));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    // Which relic to return on making a copy of this relic.
    @Override
    public AbstractRelic makeCopy() {
        return new ThievesMask();
    }
}
