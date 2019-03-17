package thiefmod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.CardIgnore;
import thiefmod.powers.Common.BackstabPower;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static thiefmod.ThiefMod.getModID;

@CardIgnore
public abstract class AbstractBackstabCard extends CustomCard {
    public int backstabNumber;
    public int baseBackstabNumber;
    public boolean upgradedBackstabNumber;
    public boolean isBackstabNumberModified;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

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

    public static boolean canBackstab() {
        if (AbstractDungeon.player.cardsPlayedThisTurn < 2 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            return true;
        } else {
            return false;
        }
    }

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

    public abstract String flavortext();

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (flavortext() != null) {
            tips.add(new TooltipInfo(FLAVOR_STRINGS[0], flavortext()));
        } else {
            tips.add(new TooltipInfo(FLAVOR_STRINGS[0], FLAVOR_STRINGS[0]));
        }
        return tips;
    }
}