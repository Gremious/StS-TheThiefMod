package thiefmod.cards.shadowstep;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.common.StealCardAction;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.ShadowstepPower;

public class Multitask extends AbstractBackstabCard {
    //TODO: This one needs a p big description change, since it has many parts that can potentially be =/> 1; Make sure it pluralizes correctly.
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("Multitask");
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    
    private static final int BACKSTAB = 3;
    private static final int UPGRADED_PLUS_BACKSTAB = -1;
    // /STAT DECLARATION/
    
    public Multitask() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ExhaustiveVariable.setBaseValue(this, 2);
        FleetingField.fleeting.set(this, true);
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;
        tags.add(ThiefCardTags.SHADOWSTEP);
        tags.add(ThiefCardTags.STEALING);
    }
    
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DiscardAction(p, p, backstabNumber, false));
        act(new ApplyPowerAction(p, p, new ShadowstepPower(p, p, magicNumber), 1));
        act(new StealCardAction(magicNumber, 1, ADD_RANDOM, AbstractDungeon.player.hand, ADD_UPGRADED));
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
            upgradeBaseCost(UPGRADE_COST);
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}