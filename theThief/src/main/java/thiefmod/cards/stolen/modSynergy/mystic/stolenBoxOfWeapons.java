package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.cards.BladeMaster;
import mysticmod.patches.MysticEnum;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.util.DiscoverCardAction;
import thiefmod.cards.abstracts.AbstractStolenMysticCard;
import thiefmod.patches.DiscoveryPatch;

@CardNoSeen
public class stolenBoxOfWeapons extends AbstractStolenMysticCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenBoxOfWeapons");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final String IMG = (ThiefMod.hasMysticMod ? BladeMaster.IMG_PATH : loadLockedCardImage(TYPE));
    private static final int COST = 1;
    private static final int MAGIC = 1;
    // /STAT DECLARATION/
    private static final int UPGRADED_MAGIC = 2;
    
    public stolenBoxOfWeapons() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            CardGroup threeRandomArtes = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            while (threeRandomArtes.size() < 3) {
                AbstractCard c = MysticMod.returnTrulyRandomArte();
                if (!DiscoveryPatch.cardUtil.containsByID(threeRandomArtes.group, c)) {
                    threeRandomArtes.addToTop(c);
                }
            }
            act(new DiscoverCardAction(threeRandomArtes));
            threeRandomArtes.clear();
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