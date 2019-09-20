package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BouqetOKnivesAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;

    public BouqetOKnivesAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.duration = 0.01F;
        this.numTimes = numTimes;
    }

    public void update() {
        if (target == null) {
            isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            isDone = true;
        } else {
            if (target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
                target.damage(info);
                if (numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --numTimes;
                    AbstractDungeon.actionManager.addToTop(new BouqetOKnivesAction(AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.miscRng), info, numTimes));
                }

                AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
            }

            isDone = true;
        }
    }
}
