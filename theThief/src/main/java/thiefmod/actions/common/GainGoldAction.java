package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class GainGoldAction extends AbstractGameAction {
    private int goldAmount;
    private DamageInfo info;
    private AbstractCreature attackTarget;

    public GainGoldAction(AbstractCreature powerOwner, AbstractCreature powerSource, int goldAmount, AbstractCreature attackTarget, DamageInfo info) {
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;

        this.target = powerOwner;
        this.source = powerSource;
        this.goldAmount = goldAmount;

        this.attackTarget = attackTarget;
        this.info = info;
    }

    public GainGoldAction(AbstractCreature powerOwner, AbstractCreature powerSource, int goldAmount) {
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;

        this.target = powerOwner;
        this.source = powerSource;
        this.goldAmount = goldAmount;
    }

    public void update() {
        com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");

        if (attackTarget != null) {
            if ((((AbstractMonster) this.attackTarget).isDying || this.attackTarget.currentHealth <= 0) && !this.attackTarget.halfDead) {

                target.gold += goldAmount;

                for (int i = 0; i < this.goldAmount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                    isDone = true;

            }

        } else {

            if (target.gold < -goldAmount) {
                goldAmount = -target.gold;
            }
            target.gold += goldAmount;

            isDone = true;
        }
        isDone = true;
    }

}