package thiefmod.cards.stolen.modSynergy.bard;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.GreaterMagicWeapon;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;
import thiefmod.powers.Unique.StolenGreatHornPower;

public class StolenGreatHorn extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenGreatHorn");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    
    private static final int MAGIC = 2;
    
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    public StolenGreatHorn() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheThiefEnum.THE_THIEF);
        magicNumber = baseMagicNumber = MAGIC;
        if (ThiefMod.hasBard) {
            portrait = new GreaterMagicWeapon().portrait;
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(p, p, new StolenGreatHornPower(p, p, 1), 1));
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasBard) {
            return AbstractBardCard.getPortraitImage(new GreaterMagicWeapon());
        } else {
            return super.getPortraitImage();
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}