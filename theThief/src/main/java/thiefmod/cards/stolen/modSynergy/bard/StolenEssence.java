package thiefmod.cards.stolen.modSynergy.bard;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.LifeDrain;
import com.evacipated.cardcrawl.mod.bard.cards.MnemonicVestments;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;

public class StolenEssence extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenVestments");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 7;
    private static final int UPGRADED_MAGIC = 10;
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    public StolenEssence() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, Bard.Enums.BARD);
        magicNumber = baseMagicNumber = MAGIC;
        if (ThiefMod.hasBard) {
            portrait = new MnemonicVestments().portrait;
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new AddTemporaryHPAction(p, p, magicNumber));
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasBard) {
            return AbstractBardCard.getPortraitImage(new MnemonicVestments());
        } else {
            return super.getPortraitImage();
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPGRADED_MAGIC);
            upgradeName();
            
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}