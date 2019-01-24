package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EchoPower;
import thiefmod.ThiefMod;

// Empty Base

public class StolenCodeButWaitPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ThiefMod.makeID("StolenCodeButWaitPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);

    public StolenCodeButWaitPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.updateDescription();
    }


    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new EchoPower(owner, 1), 1));

        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}


