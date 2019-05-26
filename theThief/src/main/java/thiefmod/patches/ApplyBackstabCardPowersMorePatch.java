package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.cards.abstracts.AbstractBackstabCard;

@SpirePatch(
        clz = GameActionManager.class,
        method = "getNextAction"
)
public class ApplyBackstabCardPowersMorePatch {
    protected static final Logger logger = LogManager.getLogger(ApplyBackstabCardPowersMorePatch.class.getName());
    
    public static void Postfix(GameActionManager __instance) {
        if (__instance.turnHasEnded && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            logger.info(ApplyBackstabCardPowersMorePatch.class.getSimpleName() + " triggered");
            applyStartOfTurnBackstabCards(__instance);
        }
    }
    
    private static void applyStartOfTurnBackstabCards(GameActionManager act) {
        logger.info("Can Backstab is " + AbstractBackstabCard.canBackstab());
        logger.info("Can Backstab for Descriptions is " + AbstractBackstabCard.canBackstabDesc());
        logger.info("These two should always match.");
        logger.info("Cards Played this turn: " + AbstractDungeon.player.cardsPlayedThisTurn);
        logger.info("Cards Played this turn are: " + act.cardsPlayedThisTurn + "(" + act.cardsPlayedThisTurn.size() + ")");
        
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractBackstabCard)
                c.applyPowers();
            logger.info("Applying Powers for " + c);
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractBackstabCard)
                c.applyPowers();
            logger.info("Applying Powers for " + c);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractBackstabCard)
                c.applyPowers();
            logger.info("Applying Powers for " + c);
        }
    }
}