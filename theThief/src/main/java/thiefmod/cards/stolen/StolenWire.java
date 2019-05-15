package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ChokePower;
import thiefmod.CardNoSeen;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

@CardNoSeen
public class StolenWire extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = thiefmod.ThiefMod.makeID("StolenWire");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 5;
    private static final int UPGRADED_PLUS_MAGIC = 6;
    // /STAT DECLARATION/
    
    public StolenWire() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, AbstractPlayer.PlayerClass.THE_SILENT);
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
    }
    
    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        act(new ApplyPowerAction(m, p, new ChokePower(m, magicNumber), magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_PLUS_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}