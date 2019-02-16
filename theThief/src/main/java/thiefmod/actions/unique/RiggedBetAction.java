package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thiefmod.actions.common.StealCardAction;

public class RiggedBetAction extends AbstractGameAction {
    private float startingDuration;

    private CardGroup ADD_LOCATION;
    private boolean ADD_RANDOM;
    private boolean ADD_UPGRADED;

    public RiggedBetAction(CardGroup ADD_LOCATION, boolean ADD_RANDOM, boolean ADD_UPGRADED) {
        target = AbstractDungeon.player;
        actionType = ActionType.WAIT;
        startingDuration = Settings.ACTION_DUR_FAST;

        duration = Settings.ACTION_DUR_FAST;

        this.ADD_LOCATION = ADD_LOCATION;
        this.ADD_RANDOM = ADD_RANDOM;
        this.ADD_UPGRADED = ADD_UPGRADED;

    }

    @Override
    public void update() {
        if (duration == startingDuration) {
            int count = AbstractDungeon.player.hand.size();
            if (count != 0) {
                AbstractDungeon.actionManager.addToTop(new StealCardAction(count, 1, ADD_RANDOM, ADD_LOCATION, ADD_UPGRADED));
                AbstractDungeon.actionManager.addToTop(new DiscardAction(this.target, this.target, count, true));
            }
            isDone = true;
        }

    }
}
