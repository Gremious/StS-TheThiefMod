package thiefmod.actions.common;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.CorruptionPower;

public class LimboToHandAction extends AbstractGameAction {
    private AbstractCard card;

    public LimboToHandAction(AbstractCard card) {
        this.card = card;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                isDone = true;
            } else if (AbstractDungeon.player.limbo.isEmpty()) {
                isDone = true;
            } else if (AbstractDungeon.player.limbo.contains(card)) {

                AbstractDungeon.player.hand.addToHand(card);
                card.unhover();
                card.setAngle(0.0F, true);
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                card.unfadeOut();        // Exhaust cards need this specifically not to break everything.
                card.fadingOut = false;
                if (AbstractDungeon.player.hasPower(CorruptionPower.POWER_ID) && card.type == AbstractCard.CardType.SKILL) {
                    card.setCostForTurn(-99);
                }
                AbstractDungeon.player.limbo.removeCard(card);

            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }

        tickDuration();
    }
}
