package thiefmod.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;

public class ShadowCalamity extends AbstractBackstabCard implements StartupCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("ShadowCalamity");
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 1;
    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DAMAGE = 10;
    // /STAT DECLARATION/
    
    public ShadowCalamity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        ExhaustiveVariable.setBaseValue(this, 2);
        GraveField.grave.set(this, true);
        magicNumber = baseMagicNumber = MAGIC;
        baseDamage = DAMAGE;
        isMultiDamage = true;
    }
    // Actions the card should do.
    
    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        act(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));
        return true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
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
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            //rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}