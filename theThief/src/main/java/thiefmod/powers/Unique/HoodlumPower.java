package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.patches.character.ThiefCardTags;

// Empty Base

public class HoodlumPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ThiefMod.makeID("HoodlumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public HoodlumPower(AbstractCreature owner, AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        img = ImageMaster.loadImage(IMG);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(ThiefCardTags.BACKSTAB)) {
            if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size() <= 0) {
                flash();
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, amount));
                AbstractDungeon.actionManager.addToBottom(new DiscardAction(owner, owner, amount, false));

            }
        } else if (card.hasTag(ThiefCardTags.STOLEN)) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(owner, amount));
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(owner, owner, amount, false));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }


}


