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
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
                this.isDone = true;
            } else if (AbstractDungeon.player.limbo.isEmpty()) {
                this.isDone = true;
            } else if (AbstractDungeon.player.limbo.contains(card)) {

                AbstractDungeon.player.hand.addToHand(card);
                card.unhover();
                this.card.setAngle(0.0F, true);
                this.card.lighten(false);
                this.card.drawScale = 0.12F;
                this.card.targetDrawScale = 0.75F;
                this.card.applyPowers();
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

        this.tickDuration();
    }
}
