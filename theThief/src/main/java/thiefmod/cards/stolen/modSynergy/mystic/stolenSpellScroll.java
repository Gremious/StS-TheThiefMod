package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.cards.Fireball;
import mysticmod.cards.Snowball;
import mysticmod.patches.MysticEnum;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;

@CardNoSeen
public class stolenSpellScroll extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("stolenSpellScroll");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 0;
    private static final int MAGIC = 1;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? Fireball.ALTERNATE_IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenSpellScroll() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(true, MysticMod.returnTrulyRandomSpell()));
        } else {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(false, MysticMod.returnTrulyRandomSpell()));
        }
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeName();
            initializeDescription();
        }
    }
}