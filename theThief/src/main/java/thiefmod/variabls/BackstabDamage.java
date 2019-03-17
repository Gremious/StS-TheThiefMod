package thiefmod.variabls;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import thiefmod.cards.abstracts.AbstractBackstabCard;

public class BackstabDamage extends DynamicVariable {

    @Override
    public String key() {
        return "theThief:BackstabTimesDamage";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isDamageModified;
//        return ((AbstractBackstabCard) card).isBackstabNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractBackstabCard) card).backstabNumber * card.damage;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractBackstabCard) card).baseBackstabNumber * card.baseDamage;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedDamage;
//      return ((AbstractBackstabCard) card).upgradedBackstabNumber;
    }
}