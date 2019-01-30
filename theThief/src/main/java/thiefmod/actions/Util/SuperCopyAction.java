package thiefmod.actions.Util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;

import java.util.Objects;

public class SuperCopyAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private AbstractCard c;
    private String location;
    private String keyword;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:CopyToHandAction");
    private static final String UITEXT[] = uiStrings.TEXT;

    /*
     * Will not add they keyword if card already has it.
     * keywords:  "Exhaust", "Ethereal"
     * location: "Hand", "Draw", "Discard"
     *
     */
    public SuperCopyAction(AbstractCard c, final String keyword, final String location) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.location = location;
        this.keyword = keyword;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            //  AbstractCard c = card.makeStatEquivalentCopy();

            if (Objects.equals(keyword, "Exhaust")) {
                if (!c.exhaust) {
                    c.exhaust = true;
                    c.rawDescription = c.rawDescription + UITEXT[0];
                }
            } else if (Objects.equals(keyword, "Ethereal")) {
                if (!c.isEthereal) {
                    c.isEthereal = true;
                    c.rawDescription = c.rawDescription + UITEXT[1];
                }
            } else{

                logger.info("The Super Copy Action didn't find the keyword you specified.");
            }

            c.initializeDescription();

            if (Objects.equals(location, "Hand")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
            } else if (Objects.equals(location, "Draw")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            } else if (Objects.equals(location, "Discard")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
            } else {
                logger.info("The Super Copy Action didn't find ether hand, deck or discard.");
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            this.tickDuration();
        }
        this.isDone = true;
    }


}
