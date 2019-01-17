package trash;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FetchAlteredCardFromDiscardAction extends AbstractGameAction {

    private boolean doRefun = false;

    public FetchAlteredCardFromDiscardAction(boolean doRefun, int refundAmount) {
        this.doRefun = doRefun;
        amount = refundAmount;
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.discardPile, 1, DiscardPileToHandAction.TEXT[0], false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractCard copy = makeAlteredCopy(c);

            }

            isDone = true;
        }
        tickDuration();
    }

    private AbstractCard makeAlteredCopy(AbstractCard card) {
        AbstractCard copy = card.makeSameInstanceOf();
        copy.modifyCostForCombat(-999);
        if (doRefun) {
            if (ExhaustiveField.ExhaustiveFields.baseExhaustive.get(copy) <= 0) {
                copy.rawDescription += " NL Exhaustive !stslib:ex!.";
            }
            ExhaustiveField.ExhaustiveFields.baseExhaustive.set(copy, amount);
            ExhaustiveField.ExhaustiveFields.exhaustive.set(copy, amount);
            copy.exhaust = false;
        } else {
            if (!copy.exhaust) {
                copy.rawDescription += " NL Exhaust.";
            }
            copy.exhaustOnUseOnce = copy.exhaust = true;
        }
        copy.initializeDescription();

        return copy;
    }

}
