package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class GuiltTripAction extends AbstractGameAction {
    private int vulnNum;
    private int strenghtDownNum;
    private AbstractMonster targetMonster;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
    public static final String TEXT[] = uiStrings.TEXT;

    public GuiltTripAction(AbstractCreature source, AbstractMonster targetMonster, int vulnNum, int strenghtDownNum) {
        duration = 0.0F;
        actionType = ActionType.WAIT;
        this.vulnNum = vulnNum;
        this.strenghtDownNum = strenghtDownNum;
        this.targetMonster = targetMonster;
        this.source = source;
    }


    public void update() {
        if (targetMonster.intent == Intent.ATTACK || targetMonster.intent == Intent.ATTACK_BUFF || targetMonster.intent == Intent.ATTACK_DEBUFF || targetMonster.intent == Intent.ATTACK_DEFEND) {
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        targetMonster, source, new VulnerablePower(targetMonster, vulnNum, false), vulnNum));

                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                        targetMonster, source, new StrengthPower(targetMonster, -strenghtDownNum), -strenghtDownNum));

            }
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
        isDone = true;
    }
}
