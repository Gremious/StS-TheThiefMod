package thiefmod.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SmilingMask;
import com.megacrit.cardcrawl.shop.OnSaleTag;
import com.megacrit.cardcrawl.shop.ShopScreen;
import javassist.CtBehavior;
import thiefmod.relics.ThievesMask;


@SpirePatch(
        clz = ShopScreen.class,
        method = "initCards"
)

public class ThievesMaskPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"saleCard"}
    )

    public static void insert(ShopScreen __instance, @ByRef AbstractCard[] saleCard) {
        if (AbstractDungeon.player.hasRelic(SmilingMask.ID) && AbstractDungeon.player.hasRelic(ThievesMask.ID)) {
            saleCard[0].price = 0;
        }
    }

    //TODO: If the player has tag-bag, cards cost go into negative. We should make sure that doesn't happen.
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(OnSaleTag.class);
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}

