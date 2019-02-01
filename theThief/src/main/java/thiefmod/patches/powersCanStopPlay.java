package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.hooks.CanUsePower;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "canPlay")
public class powersCanStopPlay {

    public static boolean Postfix(boolean result, AbstractCard __canPlay_instance, AbstractCard card) {
        boolean retValue = result;

        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power instanceof CanUsePower) {
                retValue = ((CanUsePower) power).canUse(card);
            }
        }
        return retValue;
    }
}
