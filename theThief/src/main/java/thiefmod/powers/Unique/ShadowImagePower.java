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
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.DamageAmountToTake = DamageAmountToTake;
        
        this.owner = owner;
        this.source = source;

        this.amount = amount;

        this.updateDescription();
    }

	@Override
	public void atStartOfTurn() {

        this.updateDescription();

        AbstractDungeon.actionManager.addToBottom(new DamageAction(
                this.owner, (new DamageInfo(this.owner, this.DamageAmountToTake, DamageInfo.DamageType.THORNS)),
                AbstractGameAction.AttackEffect.FIRE));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));

    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() 
    {

        this.description = DESCRIPTIONS[0] + this.DamageAmountToTake + DESCRIPTIONS[1];}



}


