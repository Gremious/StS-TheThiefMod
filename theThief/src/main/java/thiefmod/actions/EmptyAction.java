package thiefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

@Deprecated
public class EmptyAction extends AbstractGameAction {


    public EmptyAction() {
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if ((!AbstractDungeon.isScreenUp) && (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())) {
                AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                    if (card == c) {
                        AbstractDungeon.player.drawPile.removeCard(c);
                    }
                }
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.effectList.add(new PurgeCardEffect(c));
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }
        }
        tickDuration();
    }
}