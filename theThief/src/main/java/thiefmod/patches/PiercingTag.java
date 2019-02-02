package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class PiercingTag
{
    @SpireEnum
    public static AbstractCard.CardTags THIEF_PIERCING;

    @SpirePatch(
            clz=AbstractCard.class,
            method="makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy
    {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance)
        {
            if (__instance.hasTag(THIEF_PIERCING) != __result.hasTag(THIEF_PIERCING)) {
                if (__instance.hasTag(THIEF_PIERCING)) {
                    __result.tags.add(THIEF_PIERCING);
                } else {
                    __instance.tags.remove(THIEF_PIERCING);
                }
            }
            return __result;
        }
    }
}
