package thiefmod.variabls;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thiefmod.cards.abstracts.AbstractBackstabCard;

public class ThiefSecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return "theThief:Magic";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractBackstabCard) card).isBackstabNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractBackstabCard) card).backstabNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractBackstabCard) card).baseBackstabNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractBackstabCard) card).upgradedBackstabNumber;
    }
}