package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import thiefmod.ThiefMod;

public class EmptyCard extends CustomCard {

	
	
// TEXT DECLARATION 
	
	public static final String ID = thiefmod.ThiefMod.makeID("AAAEmptyCard");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);

	public static final	String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/

// STAT DECLARATION 	
	
	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = CardColor.COLORLESS;

	private static final int COST = 0;	
	private static final int UPGRADE_COST = 0;	
	
	private static final int DAMAGE = 6;
	private static final int UPGRADE_PLUS_DAMAGE = 3;
	
	private static final int BLOCK = 6;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	
	private static final int MAGIC = 1;	
	private static final int UPGRADED_PLUS_MAGIC = 1;	
	
// /STAT DECLARATION/
	
	public EmptyCard() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,COLOR,RARITY,TARGET);
		this.exhaust = true;
		this.baseDamage = DAMAGE;
		this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = MAGIC;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
/*
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new SimilarSkillsPower(p, this.magicNumber), this.magicNumber));
		
		AbstractDungeon.actionManager.addToBottom(new thiefmod.actions.common.StealCardAction(
				this.magicNumber, ADD_RANDOM, true, ADD_LOCATION, ADD_UPGRADED));
*/		
	}
		
	
    @Override
    public AbstractCard makeCopy() {
        return new EmptyCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        	this.upgradeBaseCost(UPGRADE_COST);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}