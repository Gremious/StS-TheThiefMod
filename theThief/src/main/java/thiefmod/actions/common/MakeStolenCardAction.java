package thiefmod.actions.common;

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
    
    public static final UIStrings uiStealStrings = CardCrawlGame.languagePack.getUIString("theThief:StealCardUtil");
    private static final String[] STEAL_STRINGS = uiStealStrings.TEXT;
    
    public MakeStolenCardAction(AbstractCard c, final CardGroup addLocation) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.addLocation = addLocation;
    }
    
    public MakeStolenCardAction(AbstractCard c, Integer setCost, final CardGroup addLocation) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.c = c;
        this.addLocation = addLocation;
        
        this.setCost = setCost;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            
            if (!c.hasTag(ThiefCardTags.STOLEN)) {
                c.tags.add(ThiefCardTags.STOLEN);
            }
            
            if (!c.exhaust) {
                c.exhaust = true;
                if (setCost != null) c.cost = setCost;
                c.rawDescription = c.rawDescription + KEYWORD_STRINGS[2];
                logger.info("Adding " + c + " with Exhaust.");
            }
            
            if (!c.name.startsWith(STEAL_STRINGS[5])) {
                c.name = STEAL_STRINGS[5] + c.name;
            }
            
            c.initializeDescription();
            
            if (c instanceof CustomCard) {
                ((CustomCard) c).setOrbTexture("theThiefAssets/images/cardui/512/card_thief_gray_orb.png",
                        "theThiefAssets/images/cardui/1024/card_thief_gray_orb.png");
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
            
            if (!c.hasTag(ThiefCardTags.STOLEN)) {
                c.tags.add(ThiefCardTags.STOLEN);
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
            } else if (addLocation != null) {
                addLocation.addToTop(c);
                logger.info("Adding to a custom array");
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
