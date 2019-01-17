package trash;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.SpotWeaknessAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class GuiltTripAction extends AbstractGameAction {
    private AbstractMonster targetMonster;

    public GuiltTripAction(AbstractMonster m) {

        duration = 0.0f;
        actionType = ActionType.WAIT;
        targetMonster = m;

    }

    @Override
    public void update() {
        this.isDone = this.nextIntent(this.targetMonster);

    }

    public static boolean nextIntent(AbstractMonster m) {
        if (m != null
                && (m.intent == AbstractMonster.Intent.ATTACK
                || m.intent == AbstractMonster.Intent.ATTACK_BUFF
                || m.intent == AbstractMonster.Intent.ATTACK_DEBUFF
                || m.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {

            m.setMove((byte)2, AbstractMonster.Intent.DEFEND);
          // m.setMove((byte)1, AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)m.damage.get(0)).base);

        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(
                    AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0f, SpotWeaknessAction.TEXT[0], true));
        }

        m.createIntent();
        return true;
    }
}

