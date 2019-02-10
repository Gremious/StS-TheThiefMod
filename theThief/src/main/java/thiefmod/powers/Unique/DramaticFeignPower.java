package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.hooks.CanUsePower;
import thiefmod.powers.Common.RefundCardCostPower;

public class DramaticFeignPower extends AbstractPower implements CanUsePower {

    /*
     * Prevents the player from playing any cards.
     */

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("DramaticFeignPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.RARE_POWER);


    private boolean CANPLAY = true;

    public AbstractCreature source;

    public DramaticFeignPower(AbstractCreature owner, AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        img = ImageMaster.loadImage(IMG);

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = false;
        updateDescription();

    }


    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Oooh noo! NL You got me!", 2.0f, 3.0f));

        CANPLAY = false;
    }

    @Override
    public boolean canUse(AbstractCard card) {
        return CANPLAY;
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, source, new RefundCardCostPower(
                        owner, source, amount), amount));

        AbstractDungeon.actionManager
                .addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }


}
