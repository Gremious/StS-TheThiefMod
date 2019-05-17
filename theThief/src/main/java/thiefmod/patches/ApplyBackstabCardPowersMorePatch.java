package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
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
   
    public static void Postfix(GameActionManager __instance) {
        logger.info(ApplyBackstabCardPowersMorePatch.class.getSimpleName() + " triggered");
        applyStartOfTurnBackstabCards();
    }
    
    private static void applyStartOfTurnBackstabCards() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof AbstractBackstabCard)
            c.applyPowers();
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof AbstractBackstabCard)
            c.applyPowers();
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof AbstractBackstabCard)
            c.applyPowers();
        }
    }
}