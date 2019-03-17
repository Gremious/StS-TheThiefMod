package thiefmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thiefmod.CardIgnore;
import thiefmod.powers.Common.BackstabPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static thiefmod.ThiefMod.getModID;

@CardIgnore
public abstract class AbstractBackstabCard extends CustomCard {
    public int backstabNumber;
    public int baseBackstabNumber;
    public boolean upgradedBackstabNumber;
    public boolean isBackstabNumberModified;

    public AbstractBackstabCard(final String id,
                                final String img,
                                final int cost,
                                final CardType type,
                                final CardColor color,
                                final CardRarity rarity,
                                final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        isBackstabNumberModified = false;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedBackstabNumber) {
            backstabNumber = baseBackstabNumber;
            isBackstabNumberModified = true;
        }
    }

    public void action(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void upgradeBackstabNumber(int amount) {
        super.displayUpgrades();
        baseBackstabNumber += amount;
        backstabNumber = baseBackstabNumber;
        upgradedBackstabNumber = true;
    }

    public static boolean canBackstab() {
        if (AbstractDungeon.player.cardsPlayedThisTurn < 2 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getCardImage() {
        return getModID() + "Assets/images/cards/";
    }

    public static String getCardImageBeta() {
        return getModID() + "Assets/images/cards/beta";
    }
}