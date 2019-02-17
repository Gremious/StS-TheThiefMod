package thiefmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardIgnore;
import thiefmod.powers.Common.BackstabPower;

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

        isCostModified = false;
        isCostModifiedForTurn = false;

        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isBackstabNumberModified = false;
    }

    public void displayUpgrades() {
        if (upgradedBackstabNumber) {
            backstabNumber = baseBackstabNumber;
            isBackstabNumberModified = true;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upgradeBackstabNumber(int amount) {
        baseBackstabNumber += amount;
        backstabNumber = baseBackstabNumber;
        upgradedBackstabNumber = true;
    }

    public static boolean canBackstab() {
        if (AbstractDungeon.player.cardsPlayedThisTurn < 1) {
            return true;
        } else if (AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            return true;
        } else {
            return false;
        }
    }
}