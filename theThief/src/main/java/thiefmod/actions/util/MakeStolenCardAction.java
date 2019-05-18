package thiefmod.actions.util;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
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
import thiefmod.patches.character.ThiefCardTags;

public class MakeStolenCardAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    
    private AbstractCard c;
    private CardGroup addLocation;
    private String keyword;
    private Integer setCost;
    private boolean removeKeyword;
    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;
    
    public MakeStolenCardAction(AbstractCard c, final CardGroup addLocation) {
        this(c, null, false, addLocation);
    }
    
    public MakeStolenCardAction(AbstractCard c, final String keyword, final CardGroup addLocation) {
        this(c, keyword, false, addLocation);
    }
    
    public MakeStolenCardAction(AbstractCard c, final String keyword, boolean removeKeyword, final CardGroup addLocation) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.c = c.makeStatEquivalentCopy();
        this.addLocation = addLocation;
        this.keyword = keyword;
        this.removeKeyword = removeKeyword;
    }
    
    /**
     * Will not change the card if it already has/doesn't have the keyword, respectively of what you're using the act for.
     *
     * @param c             the card that needs to be Copied.
     * @param keyword       can be "Exhaust", "Ethereal", "Unplayable". Use KEYWORD_STRINGS[] from theThief:MakeSuperCopyAction in UIString;
     * @param setCost       Will change the card cost.
     * @param removeKeyword Will remove the keyword instead of adding it.
     * @param addLocation   Hand, Draw and Discard pile groups from AbstractDungeon.player
     */
    public MakeStolenCardAction(AbstractCard c, final String keyword, Integer setCost, boolean removeKeyword, final CardGroup addLocation) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.addLocation = addLocation;
        this.keyword = keyword;
        this.setCost = setCost;
        this.removeKeyword = removeKeyword;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            
            if (!c.hasTag(ThiefCardTags.STOLEN)) {
                c.tags.add(ThiefCardTags.STOLEN);
            }
            
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
            
            if (c instanceof CustomCard){
                ((CustomCard) c).setOrbTexture("theThiefAssets/images/cardui/512/card_thief_gray_orb.png",
                        "theThiefAssets/images/cardui/1024/card_thief_gray_orb.png");;
                switch (c.type) {
                    case ATTACK:
                        ((CustomCard) c).setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_thief.png",
                                "theThiefAssets/images/cardui/1024/bg_attack_stolen_thief.png");
                        break;
                    case SKILL:
                        ((CustomCard) c).setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_thief.png",
                                "theThiefAssets/images/cardui/1024/bg_skill_stolen_thief.png");
                        break;
                    case POWER:
                        ((CustomCard) c).setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_thief.png",
                                "theThiefAssets/images/cardui/1024/bg_power_stolen_thief.png");
                        break;
                }
            }
            
            AbstractDungeon.actionManager.addToTop(new SFXAction("CARD_OBTAIN"));
            
            if (addLocation == AbstractDungeon.player.hand) {
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                }
            } else if (addLocation == AbstractDungeon.player.drawPile) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            } else if (addLocation == AbstractDungeon.player.discardPile) {
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
            } else {
                logger.info("The Super Duper Copy (Steal) Action didn't find ether hand, deck or discard.");
            }
            
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            logger.info("Final log. Super Copy (Steal) Action should be done.");
            tickDuration();
        }
        tickDuration();
    }
}
