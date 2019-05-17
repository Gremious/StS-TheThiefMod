package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;

import java.util.Iterator;

public class stolenMysticalOrbAction extends AbstractGameAction {
    private AbstractCard card;
    private int times;
    
    public stolenMysticalOrbAction(int times) {
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.WAIT;
        source = AbstractDungeon.player;
        this.card = card;
        this.times = times;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for (int i = 0; i < times; i++) {
                AbstractCard spell = MysticMod.returnTrulyRandomSpell();
                AbstractCard arte = MysticMod.returnTrulyRandomArte();
                playCard(spell);
                playCard(arte);
            }
            tickDuration();
        }
        tickDuration();
    }
    
    private void playCard(AbstractCard card) {
        target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.getCurrRoom().souls.remove(card);
        card.freeToPlayOnce = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
        card.target_y = (float) Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        
        if (!card.canUse(AbstractDungeon.player, (AbstractMonster) target)) {
            card.applyPowers();
            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
        } else {
            card.applyPowers();
            if (!this.queueContains(this.card)) {// 32
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this.card, (AbstractMonster)this.target));// 33
            }
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
            if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
            }
        }
    }
    
    
    private boolean queueContains(AbstractCard card) {
        Iterator var2 = AbstractDungeon.actionManager.cardQueue.iterator();// 41
        
        CardQueueItem i;
        do {
            if (!var2.hasNext()) {
                return false;// 46
            }
            
            i = (CardQueueItem)var2.next();
        } while(i.card != card);// 42
        
        return true;// 43
        
        // Yea that's how lazy I am.
    }
}
