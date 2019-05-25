package thiefmod.cards.stolen.modSynergy.theServant;

import blackrusemod.BlackRuseMod;
import blackrusemod.cards.Moondial;
import blackrusemod.patches.TheServantEnum;
import blackrusemod.powers.AmplifyDamagePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import mysticmod.cards.Snowball;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.powers.Unique.SimilarSkillsPower;

public class StolenMalice extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenMalice");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 2;
    
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasServant ? BlackRuseMod.makePath(BlackRuseMod.GOUGE) : loadLockedCardImage(TYPE));
    
    public StolenMalice() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheServantEnum.THE_SERVANT);
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new AmplifyDamagePower(m, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}