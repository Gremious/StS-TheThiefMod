package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

// Empty Base

public class ShadowImagePower extends AbstractPower {
    public AbstractCreature source;
    private int DamageAmountToTake;

    public static final String POWER_ID = ThiefMod.makeID("ShadowImagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public ShadowImagePower(AbstractCreature owner, AbstractCreature source, final int amount, final int DamageAmountToTake) {
        name = NAME;
        ID = POWER_ID;
        img = ImageMaster.loadImage(IMG);
        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.DamageAmountToTake = DamageAmountToTake;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {

        updateDescription();

        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                owner, (new DamageInfo(owner, DamageAmountToTake, DamageInfo.DamageType.THORNS)),
                AbstractGameAction.AttackEffect.FIRE));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {

        description = DESCRIPTIONS[0] + DamageAmountToTake + DESCRIPTIONS[1];
    }


}


