package thiefmod;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

import static thiefmod.ThiefMod.hasMysticMod;

public class CardFilter implements ClassFilter {
    private static final String PACKAGE = "thiefmod.cards.";
    private static final String MYSTIC_PACKAGE = "thiefmod.cards.stolen.mystic";

    @Override
    public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
        if (hasMysticMod) {
            if (classInfo.getClassName().startsWith(PACKAGE)) {
                return true;
            }
        } else {
            if (classInfo.getClassName().startsWith(PACKAGE)
                    && !classInfo.getClassName().startsWith(MYSTIC_PACKAGE)) {
                return true;
            }
        }
        return false;
    }
}
