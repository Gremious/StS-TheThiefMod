package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class slowerQueueCardAction extends AbstractGameAction {
    private AbstractCard card;
    private boolean slow;
    
    public slowerQueueCardAction(AbstractCard card, AbstractCreature target) {
        this(card, target, false);
    }
    
    public slowerQueueCardAction(AbstractCard card, AbstractCreature target, boolean slow) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
        this.target = target;
        this.slow = slow;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (!queueContains(card)) {
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, (AbstractMonster) target));
            }
            
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            
            tickDuration();
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