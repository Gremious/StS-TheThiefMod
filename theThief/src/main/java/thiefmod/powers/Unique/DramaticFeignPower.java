package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;

import thiefmod.ThiefMod;
import thiefmod.hooks.CanUsePower;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import thiefmod.powers.Common.RefundCardCost;

public class DramaticFeignPower extends AbstractPower implements CanUsePower {
	
/*
 * Prevents the player from playing any cards.
 */

	public static final String POWER_ID = thiefmod.ThiefMod.makeID("DramaticFeignPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = ThiefMod.makePath(ThiefMod.RARE_POWER);

	
	private boolean CANPLAY = true;
	
	public AbstractCreature source;
	public DramaticFeignPower(AbstractCreature owner, AbstractCreature source, final int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
        this.img = new Texture(IMG);
        
		this.owner = owner;
		this.source = source;
        this.amount = amount;

        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
		updateDescription();
		
	}

	
	@Override
	public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Oooh noo! NL You got me!", 2.0f, 3.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GrandFinalEffect(), 1.0f));
		CANPLAY = false;
	}

	@Override
	public boolean canUse(AbstractCard card) {
		return CANPLAY;
	}

	@Override
	public void atStartOfTurn() {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(this.owner,this.source, new RefundCardCost(
						this.owner, this.source, this.amount), this.amount));
		
		AbstractDungeon.actionManager
				.addToBottom(new RemoveSpecificPowerAction(this.owner, this.source, this.ID));
	}

	@Override
	public void updateDescription() {
		if (this.amount == 1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}

		else if (this.amount > 1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
		}
	}

	


}
