package thiefmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;

public class Sidestep extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("Sidestep");
    public static final String IMG = "theThiefAssets/images/cards/beta/Sidestep.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    // /STAT DECLARATION/
    
    public Sidestep() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(p, magicNumber), 1));
        act(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, block), block));
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}