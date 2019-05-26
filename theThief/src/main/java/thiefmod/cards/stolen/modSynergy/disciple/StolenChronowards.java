package thiefmod.cards.stolen.modSynergy.disciple;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;

public class StolenChronowards extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenChronowards");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    private static final int WARDS = 4;
    
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasDisciple ? "chrono_images/cards/VestedLegacy.png" : loadLockedCardImage(TYPE));
    
    public StolenChronowards() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, chronomuncher.patches.Enum.CHRONO_CLASS);
        magicNumber = baseMagicNumber = WARDS;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        chronomuncher.cards.Ward card = new chronomuncher.cards.Ward();
        if (this.upgraded) {
            card.upgrade();
        }
        
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card, false));
        }
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