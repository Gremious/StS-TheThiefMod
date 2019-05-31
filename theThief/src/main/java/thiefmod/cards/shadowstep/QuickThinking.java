package thiefmod.cards.shadowstep;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.ElusivePower;
import thiefmod.powers.Common.ShadowstepPower;

public class QuickThinking extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("QuickThinking");
    public static final String IMG = "theThiefAssets/images/cards/beta/QuickThinking.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    //private static final int UPGRADED_COST = 0;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    
    private static final int MAGIC_TWO = 1;
    // /STAT DECLARATION/
    
    public QuickThinking() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = MAGIC_TWO;
        tags.add(ThiefCardTags.SHADOWSTEP);
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(p, p, new ShadowstepPower(p, p, backstabNumber), backstabNumber));
        act(new DrawCardAction(p, magicNumber));
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (backstabNumber == 1 && magicNumber == 1) {
            rawDescription = EXTENDED_DESCRIPTION[1];
        } else if (backstabNumber == 1 && magicNumber != 1) {
            rawDescription = EXTENDED_DESCRIPTION[2];
        } else if (backstabNumber != 1 && magicNumber == 1) {
            rawDescription = EXTENDED_DESCRIPTION[3];
        } else {
            rawDescription = EXTENDED_DESCRIPTION[4];
        }
        initializeDescription();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            // upgradeBaseCost(UPGRADED_COST);
             upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}