package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

public class ElusivePower extends AbstractPower implements OnReceivePowerPower {
    /* Order of events:
     * Play a shadowstep card.
     * Apply Backstab! power. Apply elusive power.
     * On play Shadowstep Card: Remove Backstab only.
     * On play ANY card: Remove all.
     */
    
    public AbstractCreature source;
    public static final String POWER_ID = ThiefMod.makeID("ElusivePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/ShadowstepPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/ShadowstepPower.png");
    
    public ElusivePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        isTurnBased = false;
        
        this.owner = owner;
        this.source = source;
        
        this.amount = amount;
        
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        updateDescription();
    }
    
    @Override
    public void onInitialApplication() {
        AbstractDungeon.effectsQueue.add(new ShadowstepSmokeBoofEffect(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY));
        actionManager.addToBottom(new ApplyPowerAction(owner, source, new BackstabPower(owner, source, amount), amount));
    }
    
    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(ID)) {
            AbstractDungeon.effectsQueue.add(new ShadowstepSmokeBoofEffect(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY));
            actionManager.addToBottom(new ApplyPowerAction(owner, source, new BackstabPower(owner, source, amount), amount));
            return true;
        } else {
            return true;
        }
    }
    
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return (damage / 10) * (10 - (amount * 2));
    }
    
/*    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(ThiefCardTags.SHADOWSTEP) || (AbstractDungeon.player.hasRelic(ShadowMask.ID) && AbstractDungeon.player.hasRelic(ShadowBoots.ID))) {
        } else if (AbstractDungeon.player.hasPower(GhastlyEssencePower.POWER_ID)) {
            actionManager.addToBottom(new ReducePowerAction(owner, source, this, AbstractDungeon.player.getPower(GhastlyEssencePower.POWER_ID).amount));
        } else {
            actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
        }
    }*/
    
    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.player.hasPower(ElusivePower.POWER_ID)) {
            actionManager.addToBottom(new ReducePowerAction(owner, source, ID, (AbstractDungeon.player.getPower(ElusivePower.POWER_ID).amount - amount)));
        } else {
            actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
        }
    }
    
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + (amount * 2) + DESCRIPTIONS[1];
    }
}


