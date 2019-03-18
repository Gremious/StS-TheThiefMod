package thiefmod.variabls;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thiefmod.cards.abstracts.AbstractThiefCard;

public class ThiefSecondMagicNumber extends DynamicVariable {
    
    @Override
    public String key() {
        return "theThief:Magic";
    }
    
    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractThiefCard) card).isBackstabNumberModified;
    }
    
    @Override
    public int value(AbstractCard card) {
        return ((AbstractThiefCard) card).backstabNumber;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractThiefCard) card).baseBackstabNumber;
    }
    
    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractThiefCard) card).upgradedBackstabNumber;
    }
}