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
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_XFAST;

        target = powerOwner;
        source = powerSource;
        this.goldAmount = goldAmount;

        this.attackTarget = attackTarget;
        this.info = info;
    }

    public GainGoldAction(AbstractCreature powerOwner, AbstractCreature powerSource, int goldAmount) {
        actionType = ActionType.HEAL;

        target = powerOwner;
        source = powerSource;
        this.goldAmount = goldAmount;
    }

    public void update() {
        com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");
        if (attackTarget != null && (((AbstractMonster) attackTarget).isDying || attackTarget.currentHealth <= 0) && !attackTarget.halfDead) {
            AbstractDungeon.player.gainGold(goldAmount);

            for (int i = 0; i < goldAmount; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, true));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            isDone = true;
        } else {
            AbstractDungeon.player.gainGold(goldAmount);
            for (int i = 0; i < goldAmount; ++i) {
                AbstractDungeon.effectList.add(new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, true));
            }
            isDone = true;
        }
    }

}