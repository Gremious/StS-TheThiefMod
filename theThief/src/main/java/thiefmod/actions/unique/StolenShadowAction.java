package thiefmod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;
import java.util.Collections;

public class StolenShadowAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int costReduce;

    private ArrayList<AbstractCard> allCards = new ArrayList<>();

    public StolenShadowAction(final AbstractPlayer player, final int costReduce) {
        this.player = player;
        this.costReduce = costReduce;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.effectList.add(new BorderFlashEffect(Color.BLACK, true));
            AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_DAGGER_1"));
            AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_DAGGER_2"));
            AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_DAGGER_3"));
            AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_DAGGER_1"));

            allCards.addAll(player.drawPile.group);
            allCards.addAll(player.discardPile.group);
            allCards.addAll(player.exhaustPile.group);
            allCards.addAll(player.hand.group);

            for (AbstractCard c : allCards) {
                c.modifyCostForCombat(-costReduce);
            }
        }
        tickDuration();
    }

}

