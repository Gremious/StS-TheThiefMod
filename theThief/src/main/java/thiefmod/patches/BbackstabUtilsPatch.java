package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)

public class BbackstabUtilsPatch {
    public static ArrayList<AbstractCard> backstabsTriggeredThisTurnArray = new ArrayList();
    public static int backstabsTriggeredThisTurnInt;

    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"cardsPlayedThisTurn"}
    )

    public static void coolInsertPatch(GameActionManager __instance) {
        if (AbstractDungeon.player.cardsPlayedThisTurn <= 0) {
            backstabsTriggeredThisTurnArray.add();
            ++backstabsTriggeredThisTurnInt;
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(????????????????????????????????);
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
