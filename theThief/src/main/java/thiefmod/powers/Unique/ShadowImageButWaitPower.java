package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

public class ShadowImageButWaitPower extends AbstractPower {
    public AbstractCreature source;

    private int damageToReceive;

    public static final String POWER_ID = ThiefMod.makeID("ShadowImageButWaitPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public ShadowImageButWaitPower(AbstractCreature owner, AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        img = ImageMaster.loadImage(IMG);
        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        updateDescription();

    }

    @Override
    public void atStartOfTurn() {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                owner, source, (new ShadowImagePower(owner, source, amount, damageToReceive)), amount));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));

        damageToReceive = 0;
    }


    @Override
    public int onAttacked(DamageInfo info, int damage) {
        damageToReceive = damage;
        updateDescription();
        return 0;
    }


    public void atEndOfRound() {
        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + damageToReceive + DESCRIPTIONS[1];
    }


}


