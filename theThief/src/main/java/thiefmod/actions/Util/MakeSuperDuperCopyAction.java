package thiefmod.actions.Util;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.patches.character.ThiefCardTags;

@Deprecated
public class MakeSuperDuperCopyAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private AbstractCard c;
    private int amount;

    private CardGroup addLocation;
    private String keyword;
    private Integer setCost;
    private boolean removeKeyword;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;


    /**
     * Will not change the card if it already has/doesn't have the keyword, respectively of what you're using the action for.
     *
     * @param c             the card that needs to be Copied.
     * @param keyword       use KeywordEnums
     * @param setCost       Will change the card cost.
     * @param removeKeyword Will remove the keyword instead of adding it.
     * @param randomSpot    If adding to deck or discard, should it add to a random spot or to the top?
     * @param amount        How many copies this action will add.
     * @param addLocation   Hand, Draw and Discard pile groups from AbstractDungeon.player
     */
    public MakeSuperDuperCopyAction(AbstractCard c, final String keyword, boolean removeKeyword, Integer setCost, int amount, final CardGroup addLocation, boolean randomSpot) {
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.CARD_MANIPULATION;

        this.c = c.makeStatEquivalentCopy();
        this.keyword = keyword;
        this.removeKeyword = removeKeyword;
        this.setCost = setCost;
        this.amount = amount;
        this.addLocation = addLocation;
    }

    public static class KeywordEnums {
        public static enum KEYWORD {Exhaust, Ethereal, Unplayable, Innate,}
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (keyword != null) {
                if (keyword.equals(KEYWORD_STRINGS[0])) {
                    if (removeKeyword) {
                        if (c.exhaust) {
                            c.exhaust = false;
                            if (setCost != null) c.cost = setCost;
                            c.rawDescription = c.rawDescription.replaceAll(KEYWORD_STRINGS[1], "");
                            logger.info("Adding " + c + " with REMOVED Exhaust.");
                        }
                    } else {
                        if (!c.exhaust) {
                            c.exhaust = true;
                            if (setCost != null) c.cost = setCost;
                            c.rawDescription = c.rawDescription + KEYWORD_STRINGS[2];
                            logger.info("Adding " + c + " with Exhaust.");
                        }
                    }
                } else if (keyword.equals(KEYWORD_STRINGS[3])) {
                    if (removeKeyword) {
                        if (c.isEthereal) {
                            c.isEthereal = false;
                            if (setCost != null) c.cost = setCost;
                            c.rawDescription = c.rawDescription.replaceAll(KEYWORD_STRINGS[4], "");
                            logger.info("Adding " + c + " with REMOVED Ethereal.");
                        }
                    } else {
                        if (!c.isEthereal) {
                            c.isEthereal = true;
                            if (setCost != null) c.cost = setCost;
                            c.rawDescription = c.rawDescription + KEYWORD_STRINGS[5];
                            logger.info("Adding " + c + " with Ethereal.");
                        }
                    }
                } else if (keyword.equals(KEYWORD_STRINGS[6])) { // makeStatEquivCopy does preserve cost, but not "Unplayable." description.
                    if (removeKeyword) {
                        if (c.cost == -2) {
                            if (setCost != null) {
                                c.cost = setCost;
                            } else {
                                c.cost = 1;
                            }
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
            }
            c.initializeDescription();

            AbstractDungeon.actionManager.addToTop(new SFXAction("CARD_OBTAIN"));

            if (addLocation == AbstractDungeon.player.hand) {
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                    if (c.hasTag(ThiefCardTags.RARE_FIND))
                        AbstractDungeon.effectList.add(new CardFlashVfx(c, Color.GOLD));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                    if (c.hasTag(ThiefCardTags.RARE_FIND))
                        AbstractDungeon.effectList.add(new CardFlashVfx(c, Color.GOLD));
                }

            } else if (addLocation == AbstractDungeon.player.drawPile) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
                if (c.hasTag(ThiefCardTags.RARE_FIND)) AbstractDungeon.effectList.add(new CardFlashVfx(c, Color.GOLD));

            } else if (addLocation == AbstractDungeon.player.discardPile) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                if (c.hasTag(ThiefCardTags.RARE_FIND)) AbstractDungeon.effectList.add(new CardFlashVfx(c, Color.GOLD));

            } else {
                logger.info("The Super Duper Copy Action didn't find ether hand, deck or discard.");
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            logger.info("Final log. Super Copy Action should be done.");
            //    tickDuration();
        }
        tickDuration();
    }
}
