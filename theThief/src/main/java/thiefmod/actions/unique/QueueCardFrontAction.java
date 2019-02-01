package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class QueueCardFrontAction extends AbstractGameAction {
    private AbstractCard card;
    public int queueIndex;

    public QueueCardFrontAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public QueueCardFrontAction(AbstractCard card, AbstractCreature target, int queueIndex) {
        duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.target = target;
        this.queueIndex = queueIndex;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (card == null) {
                AbstractDungeon.actionManager.cardQueue.add(queueIndex, new CardQueueItem());
            } else if (!queueContains(card)) {
                AbstractDungeon.actionManager.cardQueue.add(queueIndex, new CardQueueItem(card, (AbstractMonster) target));
            }

            isDone = true;
        }

    }

    private boolean queueContains(AbstractCard card) {
        Iterator var2 = AbstractDungeon.actionManager.cardQueue.iterator();

        CardQueueItem i;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            i = (CardQueueItem) var2.next();
        } while (i.card != card);

        return true;
    }
}
