package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thiefmod.powers.Common.BackstabPower;
import thiefmod.powers.Common.ElusivePower;
import thiefmod.vfx.ShadowstepSmokeBoofEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager;

public class ShadowstepAction extends AbstractGameAction {
    
    public ShadowstepAction(final AbstractCreature target, final AbstractCreature source, final int amount) {
        actionType = ActionType.POWER;
        duration = Settings.ACTION_DUR_XFAST;
        
        this.target = target;
        this.source = source;
        this.amount = amount;
    }
    
    @Override
    public void update() {
        AbstractDungeon.effectsQueue.add(new ShadowstepSmokeBoofEffect(AbstractDungeon.player.drawX, AbstractDungeon.player.drawY));
        actionManager.addToBottom(new ApplyPowerAction(target, source, new ElusivePower(target, source, amount), amount));
        actionManager.addToBottom(new ApplyPowerAction(target, source, new BackstabPower(target, source, amount), amount));
        isDone = true;
    }
}
