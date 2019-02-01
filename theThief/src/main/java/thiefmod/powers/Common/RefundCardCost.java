package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

public class RefundCardCost extends AbstractPower {

    public AbstractCreature source;

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("RefunCostPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String[] TEXT = powerStrings.DESCRIPTIONS;

    public static final String IMG = ThiefMod.makePath(ThiefMod.RARE_POWER);


    public RefundCardCost(AbstractCreature owner, AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        updateDescription();

        this.amount = amount;

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(card.costForTurn));

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
                this.owner, this.source, this.ID));
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.source, this.ID));
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }


}
