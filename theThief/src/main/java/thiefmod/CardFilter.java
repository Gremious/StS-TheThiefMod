package thiefmod;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

public class CardFilter implements ClassFilter {
    private static final String PACKAGE = "thiefmod.cards.";
    private static final String MOD_PACKAGE = "thiefmod.cards.stolen.modSynergy";

    @Override
    public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
        if (classInfo.getClassName().startsWith(PACKAGE) && !classInfo.getClassName().startsWith(MOD_PACKAGE)) {
            return true;
        }

        return false;
    }
}
