package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.actions.SpellDiscoveryAction;
import mysticmod.cards.Snowball;
import mysticmod.cards.TomeOfSpells;
import mysticmod.patches.MysticEnum;
import mysticmod.patches.MysticTags;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.cards.abstracts.AbstractStolenMysticCard;
import thiefmod.patches.character.ThiefCardTags;

@CardNoSeen
public class stolenMysticalSpellbook extends AbstractStolenMysticCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenMysticalSpellbook");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    
    private static final int COST = 2;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 2;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? TomeOfSpells.IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenMysticalSpellbook() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
        if (ThiefMod.hasMysticMod) tags.add(MysticTags.IS_SPELL);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            act(new SpellDiscoveryAction());
        }
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (magicNumber >= 2) {
            rawDescription = UPGRADE_DESCRIPTION;
        } else {
            rawDescription = DESCRIPTION;
        }
        initializeDescription();
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}