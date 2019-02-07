package thiefmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
public class BottledLiverField {
    public static SpireField<Boolean> inBottledLiverField = new SpireField<>(() -> false);

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            inBottledLiverField.set(result, inBottledLiverField.get(self));
            return result;
        }
    }

}
