package thiefmod.cards.stolen.modSynergy.bard;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.MagnumOpus;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.powers.InspirationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;

public class StolenCredit extends AbstractStolenCard {
    
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenCredit");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    
    private static final int MAGIC = 1;
    
    private static final int SECOND_MAGIC_NUM = 100;
    
    
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    // (Ether Studied strike art or magnum opus art)
    
    public StolenCredit() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, Bard.Enums.BARD);
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = SECOND_MAGIC_NUM;
        if (ThiefMod.hasBard) {
            portrait = new MagnumOpus().portrait;
        }
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasBard) {
            return AbstractBardCard.getPortraitImage(new MagnumOpus());
        } else {
          return super.getPortraitImage();
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(p, p, new InspirationPower(p, 1, 100)));
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}