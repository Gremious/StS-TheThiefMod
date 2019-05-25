package thiefmod.cards.stolen.modSynergy.theServant;

import blackrusemod.BlackRuseMod;
import blackrusemod.patches.TheServantEnum;
import blackrusemod.powers.FalseFlawlessFormPower;
import blackrusemod.powers.ProtectionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;

public class StolenShirts extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenShirts");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 8;
    
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasServant ? BlackRuseMod.makePath(BlackRuseMod.DEFY) : loadLockedCardImage(TYPE));
    
    public StolenShirts() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheServantEnum.THE_SERVANT);
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ProtectionPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FalseFlawlessFormPower(p, 1), 1));
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