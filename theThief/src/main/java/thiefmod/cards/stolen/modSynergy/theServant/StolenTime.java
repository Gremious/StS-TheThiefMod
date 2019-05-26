package thiefmod.cards.stolen.modSynergy.theServant;

import blackrusemod.BlackRuseMod;
import blackrusemod.patches.TheServantEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;

public class StolenTime extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenTime");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 0;
    private static final int MAGIC = 2;
    
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasServant ? BlackRuseMod.makePath(BlackRuseMod.BOTTLED_TIME) : loadLockedCardImage(TYPE));
    
    public StolenTime() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheServantEnum.THE_SERVANT);
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}