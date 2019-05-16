package thiefmod.cards.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.CardIgnore;
import thiefmod.ThiefMod;
import thiefmod.actions.common.TestAction;
import thiefmod.patches.character.AbstractCardEnum;

@Deprecated
// @CardIgnore // Comment out to test, obv
public class AAAtestCard extends AbstractBackstabCard {
    // TEXT DECLARATION
    public static final Logger logger = LogManager.getLogger(AAAtestCard.class.getName());
    public static final String ID = ThiefMod.makeID("testCard");
    public static final String IMG = "theThiefAssets/images/cards/Test.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 0;
    // /STAT DECLARATION/
    
    public AAAtestCard() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isInnate = true;
        exhaust = true;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        logger.info(this.name + " use() start");
        logger.info("Can Backstab: " + canBackstab());
        if (canBackstab()){
            logger.info("Triggered backstab action");
            logger.info("Can Backstab: " + canBackstab());
            flash(Color.GREEN);
        }else{
            logger.info("Triggered non-backstab action");
            logger.info("Can Backstab: " + canBackstab());
            flash(Color.RED);
        }
        
        act(new MakeTempCardInHandAction(this.makeCopy()));
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}