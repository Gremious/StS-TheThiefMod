package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;

import static mysticmod.patches.MysticTags.IS_ARTE;
import static mysticmod.patches.MysticTags.IS_SPELL;

public class stolenMysticalOrbAction extends AbstractGameAction {
    private boolean exhaustCards;
    private AbstractCard card;
    private int times;
    private int i;
    
    public stolenMysticalOrbAction(boolean exhausts, int times) {
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.WAIT;
        source = AbstractDungeon.player;
        target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        
        exhaustCards = exhausts;
        this.times = times;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            
            if (card == null || card.hasTag(IS_ARTE)) {
                card = MysticMod.returnTrulyRandomSpell();
            } else if (card.hasTag(IS_SPELL)) {
                card = MysticMod.returnTrulyRandomArte();
            }
            
            AbstractDungeon.getCurrRoom().souls.remove(card);
            card.freeToPlayOnce = true;
            card.exhaustOnUseOnce = exhaustCards;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.scale;
            card.target_y = (float) Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            
            if (!card.canUse(AbstractDungeon.player, (AbstractMonster) target)) {
                if (exhaustCards) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                } else {
                    AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                    AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.limbo));
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
                }
            } else {
                card.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, target));
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
            
            i++;
            
            if (i >= times) {
                card = null;
                tickDuration();
            }
        } else {
            tickDuration();
        }
    }
}
