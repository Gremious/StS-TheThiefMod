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
        if (info != null) {
            target.damage(info);
            if ((((AbstractMonster) target).isDying || target.currentHealth <= 0) && !target.halfDead) {

                AbstractDungeon.player.gainGold(goldAmount);

                for (int i = 0; i < this.goldAmount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, true));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        } else {
            if (this.target.gold < -this.goldAmount) {
                this.goldAmount = -this.target.gold;
            }
            this.target.gold += this.goldAmount;
        }
        this.isDone = true;
    }

}