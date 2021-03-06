package thiefmod.variabls;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thiefmod.cards.abstracts.AbstractBackstabCard;

public class BackstabMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return "theThief:BackstabTimesMagic";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isMagicNumberModified;
//        return ((AbstractBackstabCard) card).isBackstabNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractBackstabCard) card).backstabNumber * card.magicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractBackstabCard) card).baseBackstabNumber * card.baseMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        //   return card.upgradedMagicNumber     ;
        return ((AbstractBackstabCard) card).upgradedBackstabNumber;
    }
}
