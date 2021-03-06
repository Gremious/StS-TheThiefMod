package thiefmod.cards.abstracts;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.CardIgnore;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static thiefmod.ThiefMod.getModID;

@CardIgnore
public abstract class AbstractThiefCard extends CustomCard {
    public int backstabNumber;
    public int baseBackstabNumber;
    public boolean upgradedBackstabNumber;
    public boolean isBackstabNumberModified;
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    
    public AbstractThiefCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
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
    
    public void act(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    public static String getCardImage() {
        return getModID() + "Assets/images/cards/";
    }
    
    public static String getCardImageBeta(String className) {
        return getModID() + "Assets/images/cards/beta" + className + ".png";
    }
}