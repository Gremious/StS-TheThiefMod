package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import thiefmod.powers.Common.ShadowstepPower;

public class OneStepAheadAction extends AbstractGameAction {
    private int magicNum;
    private int damageNum;
    private int timesNum;
    private AbstractMonster targetMonster;

    public OneStepAheadAction(int magicNum, int damageNum, int timesNum, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.magicNum = magicNum;
        this.damageNum = damageNum;
        this.timesNum = timesNum;
        this.targetMonster = m;
    }


    public void update() {
        if (this.targetMonster != null && this.targetMonster.intent == Intent.DEFEND || this.targetMonster.intent == Intent.DEFEND_BUFF || this.targetMonster.intent == Intent.ATTACK_DEBUFF || this.targetMonster.intent == Intent.DEFEND_DEBUFF) {
            for (int i = 0; i < timesNum; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(this.targetMonster,
                        new DamageInfo(AbstractDungeon.player, this.damageNum, this.damageType),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else if (this.targetMonster.intent == Intent.ATTACK || this.targetMonster.intent == Intent.ATTACK_BUFF || this.targetMonster.intent == Intent.ATTACK_DEBUFF || this.targetMonster.intent == Intent.ATTACK_DEFEND) {
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        AbstractDungeon.player, AbstractDungeon.player, new ShadowstepPower(AbstractDungeon.player, AbstractDungeon.player, this.magicNum), this.magicNum));

            }
        } else {
            AbstractDungeon.actionManager.addToTop(new TalkAction(
                    true, "It's not attacking or blocking.", 3.0f, 2.0f));
        }
        this.isDone = true;
    }
}
