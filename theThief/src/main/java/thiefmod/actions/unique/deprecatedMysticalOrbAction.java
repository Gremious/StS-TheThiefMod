package thiefmod.actions.unique;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
@Deprecated
public class deprecatedMysticalOrbAction extends AbstractGameAction {
    private float waitDuration;
    
    public deprecatedMysticalOrbAction() {
        duration = Settings.ACTION_DUR_FAST;
        waitDuration = 0.50F;
        actionType = ActionType.WAIT;
        source = AbstractDungeon.player;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractCard spell = MysticMod.returnTrulyRandomSpell();
            AbstractCard arte = MysticMod.returnTrulyRandomArte();
            
            playCard(spell);
            
            waitDuration();
            waitDuration = 0.50F;
            
            playCard(arte);
            
            waitDuration();
            waitDuration = 0.50F;
            
            tickDuration();
        }
        
        tickDuration();
    }
    
    private void waitDuration() {
        while (waitDuration > 0) {
            waitDuration -= Gdx.graphics.getDeltaTime();
        }
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
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, target));
            AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
      /*      if (!Settings.FAST_MODE) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
                AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FAST));
            }*/
        }
    }
}
