package thiefmod.actions.common;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class MakeTrueStatEquivalentCopy extends AbstractGameAction {
	private AbstractCard c;

	public MakeTrueStatEquivalentCopy(AbstractCard c) {
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.c = c;

	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {
			AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
			tickDuration();
			this.isDone = true;
		}
	}
}

