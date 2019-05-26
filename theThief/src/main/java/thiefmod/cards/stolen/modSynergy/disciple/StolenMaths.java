package thiefmod.cards.stolen.modSynergy.disciple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
// This is Chrono's Accuring card, not mine.
public class StolenMaths extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenMaths");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasDisciple ? "chrono_images/cards/Accruing.png" : loadLockedCardImage(TYPE));
    
    public StolenMaths() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, chronomuncher.patches.Enum.CHRONO_CLASS);
        baseDamage = ATTACK_DMG;
    }
    
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
    
    @Override
    public void atTurnStart() {
        this.baseDamage = fibbonacci(AbstractDungeon.player.hand.size());
    }
    
    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        this.baseDamage = fibbonacci(AbstractDungeon.player.hand.size());
    }
    
    @Override
    public void applyPowers() {
        this.baseDamage = fibbonacci(AbstractDungeon.player.hand.size());
        super.applyPowers();
        initializeDescription();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    
    public static int fibbonacci(int position) {
        return position > 1 ? fibbonacci(position - 1) + fibbonacci(position - 2) : position;
    }
}