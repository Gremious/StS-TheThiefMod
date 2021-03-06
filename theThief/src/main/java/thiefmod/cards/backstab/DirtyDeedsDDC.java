package thiefmod.cards.backstab;

import com.megacrit.cardcrawl.actions.unique.GreedAction;
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

public class DirtyDeedsDDC extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("DirtyDeedsDDC");
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
    
    private static final int COST = 1;
    
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    
    private static final int MAGIC = 5;
    
    private static final int BACKSTAB = 20;
    // /STAT DECLARATION/
    
    public DirtyDeedsDDC() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;
        exhaust = true;
        tags.add(ThiefCardTags.BACKSTAB);
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canBackstab()) {
            act(new GreedAction(m, new DamageInfo(p, damage, damageTypeForTurn), backstabNumber));
        } else {
            act(new GreedAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber));
        }
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (canBackstab()) {
            if (upgraded) {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
            } else {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
            }
        } else {
            if (upgraded) {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[4];
            } else {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[5];
            }
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
            exhaust = false;
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}