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
	private static final float DURATION = 0.01F;
	private static final float POST_ATTACK_WAIT_DUR = 0.1F;
	private int numTimes;

	private int amountFrail;
	private int amountVuln;
	private int amountPoison;

	public CorrosivePoisonAction(AbstractCreature target, int amountFrail, int amountVuln, int amountPoison, int numTimes) {
		this.target = target;
		this.actionType = ActionType.DEBUFF;
		this.duration = 0.01F;
		this.numTimes = numTimes;

		this.amountFrail = amountFrail;
		this.amountVuln = amountVuln;
		this.amountPoison = amountPoison;
	}

	public void update() {
		if (this.target == null) {
			this.isDone = true;
		} else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
			AbstractDungeon.actionManager.clearPostCombatActions();
			this.isDone = true;
		} else {
			if (this.target.currentHealth > 0) {

				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, AbstractDungeon.player, new FrailPower(this.target, this.amountFrail, false), this.amountFrail, true));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, AbstractDungeon.player, new VulnerablePower(this.target, this.amountVuln, false), this.amountVuln, true));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, this.amountPoison), this.amountPoison, true, AttackEffect.POISON));

				AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
			}

			if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
				--this.numTimes;
				AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
				AbstractDungeon.actionManager.addToBottom(new VFXAction(new PotionBounceEffect(this.target.hb.cX, this.target.hb.cY, randomMonster.hb.cX, randomMonster.hb.cY), 0.4F));
				AbstractDungeon.actionManager.addToBottom(new CorrosivePoisonAction(randomMonster, this.amountFrail, this.amountVuln, this.amountPoison, this.numTimes));
			}

			this.isDone = true;
		}
	}
}
