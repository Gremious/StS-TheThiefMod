package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AmbushAction extends AbstractGameAction {
    private AbstractCreature creature;
    private DamageInfo info;

    public AmbushAction(final AbstractCreature target, DamageInfo info) {

        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.target = target;
    }

    @Override
    public void update() {

    }
}
