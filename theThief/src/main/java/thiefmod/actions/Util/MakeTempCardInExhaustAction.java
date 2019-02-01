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
        numCards = amount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        cardToMake = card;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            AbstractCard c;
            int i;
            if (numCards < 6) {
                for (i = 0; i < numCards; ++i) {
                    c = cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            } else {
                for (i = 0; i < numCards; ++i) {
                    c = cardToMake.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            }

            duration -= Gdx.graphics.getDeltaTime();
        }

        tickDuration();
    }
}

