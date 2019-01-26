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

    public GainGoldAction(AbstractCreature target, AbstractCreature source, int goldAmount, DamageInfo info) {
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = target;
        this.source = source;
        this.info = info;
        this.goldAmount = goldAmount;
    }

    public GainGoldAction(AbstractCreature target, AbstractCreature source, int goldAmount) {
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.target = target;
        this.source = source;
        this.goldAmount = goldAmount;
    }

    public void update() {
        com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");
        if (info != null) {
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {

                AbstractDungeon.player.gainGold(this.goldAmount);

                for (int i = 0; i < this.goldAmount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        if (this.target.gold < -this.goldAmount) {
            this.goldAmount = -this.target.gold;
        }
        this.target.gold += this.goldAmount;
        this.isDone = true;
    }
}