package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.DarkSmokePuffEffect;
import thiefmod.ThiefMod;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Unique.GhastlyEssencePower;
import thiefmod.powers.Unique.ShadowMasteryPower;
import thiefmod.relics.ShadowBoots;
import thiefmod.relics.ShadowMask;
import thiefmod.util.TextureLoader;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

// Empty Base

public class ShadowstepPower extends AbstractPower implements OnReceivePowerPower {

    public AbstractCreature source;
    private int shadowMastery;

    public static final String POWER_ID = ThiefMod.makeID("ShadowstepPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/RefundCardCostPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/RefundCardCostPower.png");


    public ShadowstepPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        if (AbstractDungeon.player.hasPower(ShadowMasteryPower.POWER_ID)) {
            shadowMastery = AbstractDungeon.player.getPower(ShadowMasteryPower.POWER_ID).amount;
        } else {
            shadowMastery = 0;
        }

        updateDescription();
    }

    /*
     * Play a shadowstep card.
     * Apply Backstab! power. Apply elusive power. (This one)
     * On play Shadowstep Card: Remove Backstab only.
     * On play ANY card: Remove all.
     */

    @Override
    public void onInitialApplication() {
        actionManager.addToBottom(new ApplyPowerAction(owner, source,
                new BackstabPower(owner, source, amount), amount));

    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {

        if (power.ID.equals(ID)) {
            AbstractDungeon.effectsQueue.add(new DarkSmokePuffEffect(target.hb_x, target.hb_y));
            actionManager.addToBottom(new ApplyPowerAction(owner, source,
                    new BackstabPower(owner, source, amount), amount));
            return true;
        } else {
            return true;
        }
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (AbstractDungeon.player.hasPower(ShadowMasteryPower.POWER_ID)) {
            shadowMastery = AbstractDungeon.player.getPower(ShadowMasteryPower.POWER_ID).amount;
            return (damageAmount / 10) * (10 - amount * (shadowMastery + 1));
        } else {
            return (damageAmount / 10) * (10 - amount);
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {

        if (AbstractDungeon.player.hasPower(ShadowMasteryPower.POWER_ID)) {
            shadowMastery = AbstractDungeon.player.getPower(ShadowMasteryPower.POWER_ID).amount;
            return (damage / 10) * (10 - amount * (shadowMastery + 1));
        } else {
            return (damage / 10) * (10 - amount);
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(ThiefCardTags.SHADOWSTEP) || (AbstractDungeon.player.hasRelic(ShadowMask.ID) && AbstractDungeon.player.hasRelic(ShadowBoots.ID))) {
            return;
        } else if (AbstractDungeon.player.hasPower(GhastlyEssencePower.POWER_ID)) {
            actionManager.addToBottom(new ReducePowerAction(owner, source, this, AbstractDungeon.player.getPower(GhastlyEssencePower.POWER_ID).amount));
        } else {
            actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
        }
    }

    @Override
    public void atStartOfTurn() {

        actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
    }


    @Override
    public void updateDescription() { // tbh idk what i was thinking with this it doesn't have a >1 description
        if (AbstractDungeon.player.hasPower(ShadowMasteryPower.POWER_ID)) {
            shadowMastery = AbstractDungeon.player.getPower(ShadowMasteryPower.POWER_ID).amount;
        } else {
            shadowMastery = 0;
        }
        if (amount == 1) {
            if (owner.hasPower(ShadowMasteryPower.POWER_ID)) {
                description = DESCRIPTIONS[0] + amount * (shadowMastery + 1) + DESCRIPTIONS[1];
            } else {
                description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
            }
        } else {
            description = DESCRIPTIONS[0] + amount * (shadowMastery + 1) + DESCRIPTIONS[1];
        }
    }

}


