package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MakeExhaustedCopyAction extends AbstractGameAction {
    private AbstractCard c;

    public MakeExhaustedCopyAction(AbstractCard c) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.c = c;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
          //  AbstractCard c = card.makeStatEquivalentCopy();

            if (!c.exhaust) {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + " NL Exhaust."; //TODO: Hardcoded string to be fixed, just add it to UI strings.
            }

            c.initializeDescription();

            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));

            this.tickDuration();
        }
        this.isDone = true;
    }


}
