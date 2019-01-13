package thiefmod.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import thiefmod.ThiefMod;
import thiefmod.patches.AbstractCardEnum;
import thiefmod.powers.Unique.DramaticFeignPower;

public class DramaticFeign extends CustomCard {
	public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
	
/*
 * 3 (2) Mana, Rare, Skill Apply 2 vulnerable and stun all enemies. You cannot
 * play cards for the rest of this turn. Next turn, the first card you play is
 * free.
 */
	
	
// TEXT DECLARATION 
	
	public static final String ID = thiefmod.ThiefMod.makeID("DramaticFeign");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = ThiefMod.makePath(ThiefMod.DRAMATIC_FEIGN);
	
	public static final	String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

// /TEXT DECLARATION/

	
// STAT DECLARATION 	
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

	private static final int COST = 3;	
	private static final int UPGRADE_COST = 2;	
	
	private int MAGIC = 2;

	private int AMOUNT = 1;
// /STAT DECLARATION/
	
	public DramaticFeign() {
		super(ID,NAME,IMG,COST,DESCRIPTION,TYPE,COLOR,RARITY,TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		
		logger.info("Initialized Method");

	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {

			// Stun all enemies
			AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(mo, p));

			// Apply 2 vulnerable to all enemies
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p,
					new VulnerablePower(mo, this.magicNumber, false), this.magicNumber));
		}

		// Can't play any more cards this turn.
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new DramaticFeignPower(p, p, AMOUNT), AMOUNT));

		// Next turn, the first card you play is refunded.
		// See DramaticFeignPower (it applies powers.RefundCardCost).
	}

	// Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DramaticFeign();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }
}