package thiefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
@Deprecated
public class EmptyAction extends AbstractGameAction {
    public AbstractCreature creature;

    public EmptyAction(final AbstractCreature creature) {
        this.creature = creature;
    }

    @Override
    public void update() {

    }
}
