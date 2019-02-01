package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import thiefmod.powers.Unique.ShadowFormPower;

public class ShadowFormPlayAction extends AbstractGameAction {

    public ShadowFormPower power;

    public ShadowFormPlayAction(ShadowFormPower power) {
        this.power = power;
    }

    public void update() {

        power.playCardEffect(1);

        this.isDone = true;
    }
}
