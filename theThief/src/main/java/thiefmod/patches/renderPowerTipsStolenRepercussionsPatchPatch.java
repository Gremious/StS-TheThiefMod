package thiefmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.powers.Unique.StolenRepercussions;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "renderTip"
)
public class renderPowerTipsStolenRepercussionsPatchPatch {
    protected static final Logger logger = LogManager.getLogger(renderPowerTipsStolenRepercussionsPatchPatch.class.getName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:CallOfTheVoid");
    public static final String[] VOID_STRINGS = uiStrings.TEXT;
    
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"p", "tips"}
    )
    public static void Insert(AbstractMonster __instance, SpriteBatch sb, @ByRef AbstractPower[] p, @ByRef ArrayList<PowerTip> tips) {
        if (p[0].ID.equals(StolenRepercussions.POWER_ID)) {
            tips.add(new PowerTip(VOID_STRINGS[0], VOID_STRINGS[1], p[0].img));
        }
    }
    
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPower.class, "region48");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            // return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }
}