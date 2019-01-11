package thiefmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractBackstabCard extends CustomCard {

    public int baseBackstabNumber;
    public int backstabNumber;
    public boolean isBackstabNumberModified;
    public boolean upgradedBackstabNumber;

    public AbstractBackstabCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                                final AbstractCard.CardType type, final AbstractCard.CardColor color,
                                final AbstractCard.CardRarity rarity, final AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    public void upgradeBackstabNumber(int amount) {
        this.baseBackstabNumber += amount;
        this.backstabNumber = this.baseBackstabNumber;
        this.upgradedBackstabNumber = true;
    }
}