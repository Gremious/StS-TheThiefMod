package thiefmod.hooks;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public interface OnPreUseCardPower {
    void onPreUseCard(AbstractCard card, AbstractCreature target);
}
