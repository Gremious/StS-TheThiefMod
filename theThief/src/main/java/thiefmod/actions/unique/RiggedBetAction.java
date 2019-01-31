package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;

        this.ADD_LOCATION = ADD_LOCATION;
        this.ADD_RANDOM = ADD_RANDOM;
        this.ADD_UPGRADED = ADD_UPGRADED;

    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            int count = AbstractDungeon.player.hand.size();

            AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.target, count));
            AbstractDungeon.actionManager.addToTop(
                    new StealCardAction(count, 0, this.ADD_RANDOM, this.ADD_LOCATION, this.ADD_UPGRADED));


            this.isDone = true;
        }

    }
}
