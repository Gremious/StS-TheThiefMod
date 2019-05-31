package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;
import thiefmod.vfx.ShadowstepSmokeBoofEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

// Empty Base

public class ShadowstepPower extends AbstractPower {
    public AbstractCreature source;
    
    public static final String POWER_ID = ThiefMod.makeID("ShadowstepPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/ShadowstepPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/ShadowstepPower.png");
    
    public ShadowstepPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        updateDescription();
    }
    
    @Override
    public void onInitialApplication() {
        AbstractDungeon.effectsQueue.add(new ShadowstepSmokeBoofEffect(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY));
        actionManager.addToBottom(new ApplyPowerAction(owner, source, new ElusivePower(owner, source, amount), amount));
        actionManager.addToBottom(new ApplyPowerAction(owner, source, new BackstabPower(owner, source, amount), amount));
        actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, this));
    }
    
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
}


