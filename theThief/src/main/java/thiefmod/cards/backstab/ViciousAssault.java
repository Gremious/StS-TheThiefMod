package thiefmod.cards.backstab;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;

public class ViciousAssault extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("ViciousAssault");
    public static final String IMG = "theThiefAssets/images/cards/generic_beta_cards/purple_attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 2;
    
    private static final int DAMAGE = 4;
    
    private static final int MAGIC = 3;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    
    private static final int BACKSTAB = 4;
    private static final int UPGRADED_PLUS_BACKSTAB = 1;
    // /STAT DECLARATION/
    
    public ViciousAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;
        tags.add(ThiefCardTags.BACKSTAB);
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canBackstab()) {
            for (int i = 0; i < backstabNumber; i++) {
                act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        } else {
            for (int i = 0; i < magicNumber; i++) {
                act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (canBackstab()) {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
        } else {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
        }
        initializeDescription();
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
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}