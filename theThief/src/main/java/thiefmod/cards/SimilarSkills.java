package thiefmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import thiefmod.ThiefMod;
import thiefmod.patches.AbstractCardEnum;
import thiefmod.powers.Unique.SimilarSkillsPower;
import thiefmod.powers.Unique.SimilarSkillsPowerUpgraded;

public class SimilarSkills
extends CustomCard {
	
/*
 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
 * 
 * (2) At the start of your turn, add a random (Upgraded) Green card to your hand.
 */
	
	
// TEXT DECLARATION 
	
	public static final String ID = thiefmod.ThiefMod.makeID("SimilarSkills");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_POWER);

	public static final	String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/
	
// STAT DECLARATION 	
	
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

	private static final int COST = 2;	
	
	private static final int MAGIC = 1;
	
// /STAT DECLARATION/
	
	public SimilarSkills() {
		
		
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,COLOR,RARITY,TARGET);
		this.magicNumber = this.baseMagicNumber = MAGIC;
		
	}
	
	@Override
	public void use(final AbstractPlayer p, final AbstractMonster m) {
		if (!this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
					new SimilarSkillsPower(p, this.magicNumber), this.magicNumber));
		}
		if (this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
					new SimilarSkillsPowerUpgraded(p, this.magicNumber), this.magicNumber));
		}
	}
	
	// Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SimilarSkills();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}