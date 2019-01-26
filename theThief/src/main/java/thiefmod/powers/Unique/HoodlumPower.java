package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.patches.Unique.ThiefCardTags;

// Empty Base

public class HoodlumPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ThiefMod.makeID("HoodlumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public HoodlumPower(AbstractCreature owner, AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(ThiefCardTags.BACKSTAB)) {
            if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(this.owner, this.owner, this.amount, false));

            }
        } else if (card.hasTag(ThiefCardTags.STOLEN)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(this.owner, this.owner, this.amount, false));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }


}


