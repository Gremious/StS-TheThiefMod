package thiefmod;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

import static thiefmod.ThiefMod.*;

public class CardFilter implements ClassFilter {
    private static final String PACKAGE = "thiefmod.cards.";
    private static final String MOD_PACKAGE = "thiefmod.cards.stolen.modSynergy";

    @Override
    public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
        if (classInfo.getClassName().startsWith(PACKAGE)
                && classInfo.getClassName().startsWith(MOD_PACKAGE)) {
            if (classInfo.getClassName().toLowerCase().contains("bard") && hasBard) {
                return true;
            } else if (classInfo.getClassName().toLowerCase().contains("disciple") && hasDisciple) {
                return true;
            } else if (classInfo.getClassName().toLowerCase().contains("halation") && hasHalation) {
                return true;
            } else if (classInfo.getClassName().toLowerCase().contains("mystic") && hasMysticMod) {
                return true;
            } else if (classInfo.getClassName().toLowerCase().contains("servant") && hasServant) {
                return true;
            } else if (classInfo.getClassName().toLowerCase().contains("yohane") && hasYohane) {
                return true;
            }
        } else if (classInfo.getClassName().startsWith(PACKAGE) && !classInfo.getClassName().startsWith(MOD_PACKAGE)) {
            return true;
        } else {
            return false;
        }

   /*
        if (classInfo.getClassName().startsWith(PACKAGE)) {
            return true;
        }*/
        return false;
    }
}