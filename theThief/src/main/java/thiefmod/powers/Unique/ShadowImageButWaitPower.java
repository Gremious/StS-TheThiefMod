package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
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
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        this.updateDescription();

    }

	@Override
	public void atStartOfTurn() {

        AbstractDungeon.actionManager.addToBottom( new ApplyPowerAction(
                this.owner, this.source, (new ShadowImagePower(this.owner, this.source, this.amount, damageToReceive)),this.amount));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));

         this.damageToReceive = 0;
    }


    @Override
    public int onAttacked(DamageInfo info, int damage)
    {
        damageToReceive = damage;
        this.updateDescription();

        return damage;
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
    		this.description = DESCRIPTIONS[0] + this.damageToReceive + DESCRIPTIONS[1];}


}


