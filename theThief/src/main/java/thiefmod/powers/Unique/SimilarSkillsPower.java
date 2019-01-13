package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.*;

import thiefmod.ThiefMod;

// Empty Base

public class SimilarSkillsPower extends AbstractPower {	

	
	public static final String POWER_ID = thiefmod.ThiefMod.makeID("SimilarSkillsPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public SimilarSkillsPower(final AbstractCreature owner, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        
        this.owner = owner;

        this.amount = amount;

        this.updateDescription();
    }

	@Override
	public void atStartOfTurn() {
		AbstractCard c;
		
		for (int i = 0; i < this.amount; i++) {
			c = CardLibrary
					.getRandomColorSpecificCard(CardColor.GREEN, AbstractDungeon.cardRandomRng).makeCopy();
				c.upgrade();
				c.modifyCostForCombat(-20);
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, this.amount, true, false));
		}

		this.updateDescription();
	}
    
	@Override
	public void updateDescription() {
		if (this.amount == 1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}

		else if (this.amount > 1) {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
		}
	}


}




