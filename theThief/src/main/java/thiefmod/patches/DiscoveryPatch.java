package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpirePatch(
        clz = CardRewardScreen.class,
        method = "discoveryOpen",
        paramtypez = {}
)
public class DiscoveryPatch {
    public static boolean customDiscovery = false;
    
    // Default values
    public static CardGroup cardGroupToDiscoverFrom = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public static int amount = 3;
    public static boolean upgraded = false;
    
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"CARD_TARGET_Y"}
    )
    public static SpireReturn DiscoveryInsertPatch(CardRewardScreen __instance, final float CARD_TARGET_Y) {
        if (customDiscovery) {
            ArrayList<AbstractCard> derp = new ArrayList<>();
            
            while (derp.size() < amount) {
                AbstractCard c = cardGroupToDiscoverFrom.getRandomCard(true);
                if (upgraded) c.upgrade();
                if (cardUtil.containsByID(derp, c)) {
                    cardUtil.removeByID(cardGroupToDiscoverFrom, c);
                    break;
                }
                cardGroupToDiscoverFrom.removeCard(c);
                derp.add(c.makeCopy());
            }
            
            __instance.rewardGroup = derp;
            AbstractDungeon.isScreenUp = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.CARD_REWARD;
            AbstractDungeon.overlayMenu.showBlackScreen();
            
            try {
                Method placeCards = CardRewardScreen.class.getDeclaredMethod("placeCards", float.class, float.class);
                placeCards.setAccessible(true);
                placeCards.invoke(__instance, (float) Settings.WIDTH / 2.0F, CARD_TARGET_Y);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            
            for (AbstractCard c : __instance.rewardGroup) {
                UnlockTracker.markCardAsSeen(c.cardID);
            }
            
            reset();
            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }
    
    private static void reset() {
        customDiscovery = false;
        amount = 3;
        upgraded = false;
        cardGroupToDiscoverFrom.clear();
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "size");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
    
    public static class cardUtil {
        public static boolean containsByID(ArrayList<AbstractCard> poolToCheck, AbstractCard cardWithID) {
            for (AbstractCard c : poolToCheck) {
                if (c.cardID.equals(cardWithID.cardID)) {
                    return true;
                }
            }
            return false;
        }
        
        public static void removeByID(CardGroup groupToRemoveFrom, AbstractCard cardWithID) {
            List<String> IDsOfBaseGroup = groupToRemoveFrom.group.stream()
                    .map(c -> c.cardID)
                    .collect(Collectors.toList());
            IDsOfBaseGroup.removeIf(id -> id.equals(cardWithID.cardID));
        }
    }
}
