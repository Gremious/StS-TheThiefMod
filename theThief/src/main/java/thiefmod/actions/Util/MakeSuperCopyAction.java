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

public class MakeSuperCopyAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private AbstractCard c;
    private String addLocation;
    private String keyword;
    private boolean removeKeyword;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;

    /*
     * Will not add/remove the keyword if card already has it/doesn't have it, respectively.
     * keywords:  "Exhaust", "Ethereal", "Unplayable". Use KEYWORD_STRINGS[].
     * addLocation: "Hand", "Draw", "Discard"
     */

    public MakeSuperCopyAction(AbstractCard c, final String keyword, final String addLocation) {
        this(c, keyword, false, addLocation);
    }

    public MakeSuperCopyAction(AbstractCard c, final String keyword, boolean removeKeyword, final String addLocation) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.addLocation = addLocation;
        this.keyword = keyword;
        this.removeKeyword = removeKeyword;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (keyword.equals(KEYWORD_STRINGS[0])) {
                if (removeKeyword) {
                    if (c.exhaust) {
                        c.exhaust = false;
                        c.rawDescription = c.rawDescription.replaceAll(KEYWORD_STRINGS[1], "");
                        logger.info("Adding " + c + " with REMOVED Exhaust.");
                    }
                } else {
                    if (!c.exhaust) {
                        c.exhaust = true;
                        c.rawDescription = c.rawDescription + KEYWORD_STRINGS[2];
                        logger.info("Adding " + c + " with Exhaust.");
                    }
                }
            } else if (keyword.equals(KEYWORD_STRINGS[3])) {
                if (removeKeyword) {
                    if (c.isEthereal) {
                        c.isEthereal = false;
                        c.rawDescription = c.rawDescription.replaceAll(KEYWORD_STRINGS[4], "");
                        logger.info("Adding " + c + " with REMOVED Ethereal.");
                    }
                } else {
                    if (!c.isEthereal) {
                        c.isEthereal = true;
                        c.rawDescription = c.rawDescription + KEYWORD_STRINGS[5];
                        logger.info("Adding " + c + " with Ethereal.");
                    }
                }
            } else if (keyword.equals(KEYWORD_STRINGS[6])) {
                if (removeKeyword) {
                    if (c.cost == -2) {
                        c.cost = 1;
                        c.rawDescription = c.rawDescription.replaceAll(KEYWORD_STRINGS[7], "");
                        logger.info("Adding " + c + " with REMOVED Unplayable.");
                    }
                } else {
                    if (c.cost != -2) {
                        c.cost = -2;
                        c.rawDescription = KEYWORD_STRINGS[8] + c.rawDescription;
                        logger.info("Adding " + c + " with Unplayable.");
                    }
                }
            }

            c.initializeDescription();

            if (Objects.equals(addLocation, "Hand")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
            } else if (Objects.equals(addLocation, "Draw")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            } else if (Objects.equals(addLocation, "Discard")) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
            } else {
                logger.info("The Super Duper Copy Action didn't find ether hand, deck or discard.");
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            logger.info("Final log. Super Copy Action should be done.");
            this.tickDuration();
        }
        this.tickDuration();
    }
}
