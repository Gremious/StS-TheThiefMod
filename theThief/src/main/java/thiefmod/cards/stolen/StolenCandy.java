package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;
import thiefmod.patches.character.ThiefCardTags;

@CardNoSeen
public class StolenCandy extends AbstractStolenCard {
    
    /*
     * 0: gain 1 (2) energy
     */
    // TEXT DECLARATION
    
    public static final String ID = thiefmod.ThiefMod.makeID("StolenCandy");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    // /STAT DECLARATION/
    
    public StolenCandy() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheThiefEnum.THE_THIEF );
        magicNumber = baseMagicNumber = MAGIC;
        
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}