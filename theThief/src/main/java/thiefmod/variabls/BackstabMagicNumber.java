package thiefmod.variabls;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.cards.abstracts.AbstractThiefCard;

public class BackstabMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return "theThief:BackstabTimesMagic";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isMagicNumberModified;
//        return ((AbstractThiefCard) card).isBackstabNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractThiefCard) card).backstabNumber * card.magicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractThiefCard) card).baseBackstabNumber * card.baseMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        //   return card.upgradedMagicNumber     ;
        return ((AbstractThiefCard) card).upgradedBackstabNumber;
    }
}
