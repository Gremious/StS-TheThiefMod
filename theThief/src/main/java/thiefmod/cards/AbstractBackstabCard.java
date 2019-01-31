package thiefmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardIgnore;
import thiefmod.powers.Unique.ConArtistPower;
import thiefmod.variabls.ThiefSecondMagicNumber;

@CardIgnore
public abstract class AbstractBackstabCard extends CustomCard {

    public int backstabNumber;
    public int baseBackstabNumber;
    public boolean upgradedBackstabNumber;
    public boolean isBackstabNumberModified;

    public AbstractBackstabCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                                final AbstractCard.CardType type, final AbstractCard.CardColor color,
                                final AbstractCard.CardRarity rarity, final AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        this.isCostModified = false;
        this.isCostModifiedForTurn = false;

        this.isDamageModified = false;
        this.isBlockModified = false;
        this.isMagicNumberModified = false;
        this.isBackstabNumberModified = false;
    }

    public void displayUpgrades() {

        if (this.upgradedBackstabNumber) {
            this.backstabNumber = this.baseBackstabNumber;
            this.isBackstabNumberModified = true;
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgradeBackstabNumber(int amount) {
        this.baseBackstabNumber += amount;
        this.backstabNumber = this.baseBackstabNumber;
        this.upgradedBackstabNumber = true;
    }
}