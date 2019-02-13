package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

import java.util.Iterator;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

// Empty Base

public class DealDamageToAllNextTurnPower extends AbstractPower {
    public AbstractCreature source;
    public AbstractCreature target;
    private int damageAmount;

    public static final String POWER_ID = ThiefMod.makeID("DealDamageToAllNextTurnPower");
    public static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/DealDamageToAllNextTurnPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/DealDamageToAllNextTurnPower.png");


    public DealDamageToAllNextTurnPower(AbstractCreature owner, AbstractCreature source, AbstractCreature target, int damageAmount, final int amount) {
        name = NAME;
        ID = POWER_ID;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.owner = owner;
        this.source = source;
        this.target = target;

        this.amount = amount;
        this.damageAmount = damageAmount;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        flash();

        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        AbstractMonster mo;
        while (var3.hasNext()) {
            mo = (AbstractMonster) var3.next();
            if (!mo.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToTop(new VFXAction(
                        new AdditiveSlashImpactEffect(mo.drawX, mo.drawY, Color.GOLD), 0.05F));
            }
        }

        AbstractDungeon.actionManager.addToTop(
                new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damageAmount, false, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        if (amount > 0) {
            actionManager.addToBottom(new ReducePowerAction(owner, source, ID, 1));
        } else {
            actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + damageAmount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + damageAmount + DESCRIPTIONS[2];
        }
    }


}


