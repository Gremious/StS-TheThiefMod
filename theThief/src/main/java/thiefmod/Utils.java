package thiefmod;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.SingingBowlButton;
import com.megacrit.cardcrawl.ui.buttons.SkipCardButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Utils {

    // I have absolutely no idea how this works. I copied it from the Mad Scientist and it works. So hey it looks cool.

    // TODO: Test these bad-boy hardcoded strings in a different language.

    public static final Logger logger = LogManager.getLogger(Utils.class.getName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:StealCardUtil");
    public static final String DESCRIPTIONS[] = uiStrings.TEXT;

    public static void openCardRewardsScreen(ArrayList<AbstractCard> cards, boolean allowSkip) {
        // Based on CardRewardScreen.codexOpen
        CardRewardScreen crs = AbstractDungeon.cardRewardScreen;
        crs.rItem = null;
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "codex", true);
        ReflectionHacks.setPrivate(crs, CardRewardScreen.class, "draft", false);
        crs.codexCard = null;
        ((SingingBowlButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "bowlButton")).hide();
        if (allowSkip) {
            ((SkipCardButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).show();
        } else {
            ((SkipCardButton) ReflectionHacks.getPrivate(crs, CardRewardScreen.class, "skipButton")).hide();
        }
        crs.onCardSelect = true;
        AbstractDungeon.topPanel.unhoverHitboxes();
        crs.rewardGroup = cards;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
        AbstractDungeon.dynamicBanner.appear(DESCRIPTIONS[0]);
        AbstractDungeon.overlayMenu.showBlackScreen();
        final float CARD_TARGET_Y = (float) Settings.HEIGHT * 0.45f;
        try {
            Method method = CardRewardScreen.class.getDeclaredMethod("placeCards", float.class, float.class);
            method.setAccessible(true);
            method.invoke(crs, (float) Settings.WIDTH / 2.0f, CARD_TARGET_Y);
        } catch (Exception ex) {
            logger.error("Exception occurred when calling placeCards ", ex);
        }
        for (AbstractCard c : cards) {
            UnlockTracker.markCardAsSeen(c.cardID);
        }
    }

}