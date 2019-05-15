package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;

import static mysticmod.MysticMod.cantripsGroup;

@CardNoSeen
public class stolenBagOfMagicCantrips extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenBagOfMagicCantrips");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    
    private static final int MAGIC = 1;
    // /STAT DECLARATION/
    
    public stolenBagOfMagicCantrips() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, );
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> trinketCards = new ArrayList<>();
        trinketCards.add(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));
        trinketCards.add(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));
        trinketCards.add(cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1)));
        if (upgraded) {
            act(new DiscoverRandomFromArrayAction(trinketCards, true));
        }
        act(new DiscoverRandomFromArrayAction(trinketCards));
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
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}