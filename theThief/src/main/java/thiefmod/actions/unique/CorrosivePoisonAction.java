package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

public class CorrosivePoisonAction extends AbstractGameAction {
	private int numTimes;
	private int amountFrail;
	private int amountVuln;
	private int amountPoison;

	public CorrosivePoisonAction(AbstractCreature target, int amountFrail, int amountVuln, int amountPoison, int numTimes) {
		this.target = target;
		this.actionType = ActionType.DEBUFF;
		this.numTimes = numTimes;

		this.amountFrail = amountFrail;
		this.amountVuln = amountVuln;
		this.amountPoison = amountPoison;
	}

	public void update() {
		if (this.target == null) {
			isDone = true;
		} else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractDungeon.actionManager.clearPostCombatActions();
			isDone = true;
		} else {
			if (target.currentHealth > 0) {

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new FrailPower(target, amountFrail, false), amountFrail, true));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, amountVuln, false), amountVuln, true));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, amountPoison), amountPoison, true, AttackEffect.POISON));

				AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
			}

			if (numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				--numTimes;
				AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new PotionBounceEffect(target.hb.cX, target.hb.cY, randomMonster.hb.cX, randomMonster.hb.cY), 0.4F));
				AbstractDungeon.actionManager.addToBottom(new CorrosivePoisonAction(randomMonster, amountFrail, amountVuln, amountPoison, numTimes));
			}

			isDone = true;
		}
	}
}
