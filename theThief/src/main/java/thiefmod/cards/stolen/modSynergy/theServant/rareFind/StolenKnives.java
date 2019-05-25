package thiefmod.cards.stolen.modSynergy.theServant.rareFind;

import blackrusemod.BlackRuseMod;
import blackrusemod.cards.Rearm;
import blackrusemod.patches.TheServantEnum;
import blackrusemod.powers.KnivesPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import mysticmod.cards.BladeMaster;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Unique.StolenKnivesPower;

import static thiefmod.ThiefMod.hasServant;

@CardNoSeen
public class StolenKnives extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenKnives");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    
    private static final int COST = 1;
    
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    
    private static final int MAGIC = 1;
    
    private static final int BACKSTAB = 1;
    // /STAT DECLARATION/
    
    public static final String IMG = (hasServant ? BlackRuseMod.makePath(BlackRuseMod.REARM) : loadLockedCardImage(TYPE));
    
    public StolenKnives() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.RARE, TheServantEnum.THE_SERVANT);
        exhaust = true;
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.BLUE));
        act(new ApplyPowerAction(p, p, new KnivesPower(p, 99), 99));
        act(new ApplyPowerAction(p, p, new StolenKnivesPower(p, magicNumber, damage, new DamageInfo(p, damage, damageTypeForTurn)), magicNumber));
    }
    
    @Override
    public AbstractCard makeCopy() {
        if (!hasServant) {
            return new Madness();
        } else {
            return super.makeCopy();
        }
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}