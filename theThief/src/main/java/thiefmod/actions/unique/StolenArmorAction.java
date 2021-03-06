package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class StolenArmorAction extends AbstractGameAction {
    private int blockGain;

    public StolenArmorAction(int blockGain) {
        duration = 0.0F;
        actionType = ActionType.WAIT;
        this.blockGain = blockGain;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            isDone = true;
        } else {
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            if (card.upgraded) {

                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockGain));
            }

            isDone = true;
        }
    }
}
