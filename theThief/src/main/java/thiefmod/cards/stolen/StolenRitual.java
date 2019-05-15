package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;
import thiefmod.patches.character.ThiefCardTags;

@CardNoSeen
public class StolenRitual extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = thiefmod.ThiefMod.makeID("StolenRitual");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 0;
    private static final int UPGRADE_COST = 0;
    
    private static final int DAMAGE = 3;
    
    private static final int MAGIC = 17;
    private static final int UPGRADED_PLUS_MAGIC = 5;
    // /STAT DECLARATION/
    
    public StolenRitual() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, TheThiefEnum.THE_THIEF);
        magicNumber = baseMagicNumber = MAGIC;
        damage = baseDamage = DAMAGE;
        
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new LoseHPAction(p, p, damage));
        act(new thiefmod.actions.common.GainGoldAction(p, p, magicNumber));
        CardCrawlGame.sound.playA("VO_CULTIST_1C", 0.3f);
        act(new TalkAction(true, "CA-CAW", 2.0f, 2.0f));
        act(new WaitAction(0.1f));
        act(new WaitAction(0.1f));
        act(new WaitAction(0.1f));
        act(new WaitAction(0.1f));
        act(new WaitAction(0.1f));
        act(new TalkAction(true, "Cough..", 2.0f, 2.0f));
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