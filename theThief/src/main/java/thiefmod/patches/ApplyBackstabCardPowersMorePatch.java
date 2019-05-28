package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.cards.abstracts.AbstractBackstabCard;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class ApplyBackstabCardPowersMorePatch {
    protected static final Logger logger = LogManager.getLogger(ApplyBackstabCardPowersMorePatch.class.getName());
    private static boolean triggered = false;
    
    public static void Postfix(GameActionManager __instance) {
        if (__instance.turnHasEnded && !AbstractDungeon.getMonsters().areMonstersBasicallyDead() && !triggered) {
            logger.info(ApplyBackstabCardPowersMorePatch.class.getSimpleName() + " triggered");
            applyStartOfTurnBackstabCards(__instance);
        }
    }
    
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void booleanReset(GameActionManager __instance) {
        triggered = false;
    }
    
    private static void applyStartOfTurnBackstabCards(GameActionManager act) {
        
        if (!triggered) {
            logger.info("Can Backstab is " + AbstractBackstabCard.canBackstab());
            logger.info("Can Backstab for Descriptions is " + AbstractBackstabCard.canBackstabDesc());
            logger.info("These two should always match.");
            logger.info("Cards Played this turn: " + AbstractDungeon.player.cardsPlayedThisTurn);
            logger.info("Cards Played this turn are: " + act.cardsPlayedThisTurn + "(" + act.cardsPlayedThisTurn.size() + ")");
            
            
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c instanceof AbstractBackstabCard) {
                    c.applyPowers();
                    logger.info("Applying Powers for " + c);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractBackstabCard) {
                    c.applyPowers();
                    logger.info("Applying Powers for " + c);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof AbstractBackstabCard) {
                    c.applyPowers();
                    logger.info("Applying Powers for " + c);
                }
            }
            triggered = true;
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCurrRoom");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[2]};
        }
    }
}