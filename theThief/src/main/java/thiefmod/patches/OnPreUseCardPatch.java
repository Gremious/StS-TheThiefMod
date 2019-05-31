package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.hooks.OnPreUseCardPower;

import java.util.Iterator;

@SpirePatch(
        clz = UseCardAction.class,
        method = SpirePatch.CONSTRUCTOR
)
public class OnPreUseCardPatch {
    protected static final Logger logger = LogManager.getLogger(OnPreUseCardPatch.class.getName());
    
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(UseCardAction __instance, @ByRef AbstractCard[] card, @ByRef AbstractCreature[] target) {
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof OnPreUseCardPower) {
                ((OnPreUseCardPower) power).onPreUseCard(card[0], target[0]);
            }
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Iterator.class, "hasNext");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}