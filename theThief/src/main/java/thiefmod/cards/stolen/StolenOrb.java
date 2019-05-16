package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

@CardNoSeen
public class StolenOrb extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenOrb");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    
    private static final int COST = 0;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    // /STAT DECLARATION/
    
    public StolenOrb() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON,AbstractPlayer.PlayerClass.DEFECT );
        magicNumber = baseMagicNumber = MAGIC;
        
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ChannelAction(AbstractOrb.getRandomOrb(true)));
        act(new WaitAction(0.1F));
        act(new EvokeOrbAction(1));
        act(new WaitAction(0.1F));
        act(new DecreaseMaxOrbAction(1));
        if (upgraded) {
            act(new ChannelAction(AbstractOrb.getRandomOrb(true)));
            act(new WaitAction(0.1F));
            act(new EvokeOrbAction(1));
            act(new WaitAction(0.1F));
            act(new DecreaseMaxOrbAction(1));
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}