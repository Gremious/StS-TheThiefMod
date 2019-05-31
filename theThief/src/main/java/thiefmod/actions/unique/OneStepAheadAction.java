package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import thiefmod.actions.common.ShadowstepAction;

public class OneStepAheadAction extends AbstractGameAction {
    private int magicNum;
    private int damageNum;
    private int timesNum;
    private AbstractMonster targetMonster;
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
    public static final String TEXT[] = uiStrings.TEXT;
    
    public OneStepAheadAction(int magicNum, int damageNum, int timesNum, AbstractMonster m) {
        duration = 0.0F;
        actionType = ActionType.WAIT;
        this.magicNum = magicNum;
        this.damageNum = damageNum;
        this.timesNum = timesNum;
        targetMonster = m;
    }
    
    public void update() {
        if (targetMonster.intent == Intent.ATTACK
                || targetMonster.intent == Intent.ATTACK_BUFF
                || targetMonster.intent == Intent.ATTACK_DEBUFF
                || targetMonster.intent == Intent.ATTACK_DEFEND) {
            AbstractDungeon.actionManager.addToBottom(new ShadowstepAction(AbstractDungeon.player, AbstractDungeon.player, amount));
        } else {
            for (int i = 0; i < timesNum; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster,
                        new DamageInfo(AbstractDungeon.player, damageNum, damageType),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
        isDone = true;
    }
}
