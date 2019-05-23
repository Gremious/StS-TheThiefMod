package thiefmod.patches;

import com.evacipated.cardcrawl.mod.bard.powers.MantleOfInspirationPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.cards.stolen.modSynergy.bard.StolenCredit;

@SpirePatch(
        cls = "com.evacipated.cardcrawl.mod.bard.powers.MantleOfInspirationPower",
        method = "modifyInspiration",
        optional = true
)
public class MantleOfInspirationHardcodePatch {
    protected static final Logger logger = LogManager.getLogger(MantleOfInspirationHardcodePatch.class.getName());
    
    public static void Prefix(MantleOfInspirationPower __instance, float inspirationAmount) {
        logger.info(MantleOfInspirationHardcodePatch.class.getSimpleName() + " triggered");
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof StolenCredit) {
                ((AbstractStolenCard) c).backstabNumber += __instance.amount;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof StolenCredit) {
                ((AbstractStolenCard) c).backstabNumber += __instance.amount;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof StolenCredit) {
                ((AbstractStolenCard) c).backstabNumber += __instance.amount;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof StolenCredit) {
                ((AbstractStolenCard) c).backstabNumber += __instance.amount;
                c.initializeDescription();
            }
        }
    }
}