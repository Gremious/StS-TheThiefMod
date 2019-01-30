package thiefmod.actions.Util;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;


public class MakeTempCardInExhaustAction extends AbstractGameAction {
    private AbstractCard cardToMake;
    private int numCards;

    public MakeTempCardInExhaustAction(AbstractCard card, int amount) {
        UnlockTracker.markCardAsSeen(card.cardID);
        this.numCards = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardToMake = card;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractCard c;
            int i;
            if (this.numCards < 6) {
                for (i = 0; i < this.numCards; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            } else {
                for (i = 0; i < this.numCards; ++i) {
                    c = this.cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            }

            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}

