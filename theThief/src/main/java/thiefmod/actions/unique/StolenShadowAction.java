package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class StolenShadowAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int costReduce;

    private static ArrayList<AbstractCard> allCards;

    public StolenShadowAction(final AbstractPlayer player, final int costReduce) {
        this.player = player;
        this.costReduce = costReduce;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        allCards.addAll(player.drawPile.group);
        allCards.addAll(player.discardPile.group);
        allCards.addAll(player.exhaustPile.group);
        allCards.addAll(player.hand.group);
        for (AbstractCard c : allCards) {
            c.modifyCostForCombat(-costReduce);
        }
        tickDuration();
    }
}
