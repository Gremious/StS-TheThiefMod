package thiefmod.cards.abstracts;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.CardIgnore;

import static thiefmod.ThiefMod.getModID;

@CardIgnore
public abstract class AbstractStolenCard extends AbstractThiefCard {
    public int backstabNumber;
    public int baseBackstabNumber;
    public boolean upgradedBackstabNumber;
    public boolean isBackstabNumberModified;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public AbstractStolenCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, img, cost, type, color, rarity, target);

        isBackstabNumberModified = false;
    }

    // Second Magic Number
    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedBackstabNumber) {
            backstabNumber = baseBackstabNumber;
            isBackstabNumberModified = true;
        }
    }

    public void upgradeBackstabNumber(int amount) {
        super.displayUpgrades();
        baseBackstabNumber += amount;
        backstabNumber = baseBackstabNumber;
        upgradedBackstabNumber = true;
    }

    //==


    public void action(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static String getCardImage() {
        return getModID() + "Assets/images/cards/";
    }

    public static String getCardImageBeta() {
        return getModID() + "Assets/images/cards/beta";
    }

    public static String flavortext(String EXTENDED_DESCRIPTION) {
        return EXTENDED_DESCRIPTION;

    }
}