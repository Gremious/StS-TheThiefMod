package thiefmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.actions.common.StealCardAction;
import thiefmod.powers.Unique.IllGottenGainsPower;

import java.util.Iterator;

public class LoadedDiceAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:Actions");
    public static final String TEXT[] = uiStrings.TEXT;

    public LoadedDiceAction(final AbstractCreature source) {
        this.source = source;
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                    AbstractDungeon.actionManager.addToTop(new StealCardAction(AbstractDungeon.handCardSelectScreen.selectedCards.group.size(), 1, true, AbstractDungeon.player.hand, AbstractDungeon.player.hasPower(IllGottenGainsPower.POWER_ID)));
                    Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                    while (var1.hasNext()) {
                        AbstractCard c = (AbstractCard) var1.next();
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        GameActionManager.incrementDiscard(false);
                        c.triggerOnManualDiscard();
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

}
