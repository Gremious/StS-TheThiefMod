package thiefmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class ExhaustedCopyAction extends AbstractGameAction {
    private AbstractCard c;
    private String location;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:CopyToHandAction");
    private static final String UITEXT[] = uiStrings.TEXT;

    public ExhaustedCopyAction(AbstractCard c, final String location) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.location = location;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
          //  AbstractCard c = card.makeStatEquivalentCopy();

            if (!c.exhaust) {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + UITEXT[0];
            }

            c.initializeDescription();

            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            this.tickDuration();
        }
        this.isDone = true;
    }


}
