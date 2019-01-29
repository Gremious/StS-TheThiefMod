package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.powers.Common.ShadowstepPower;

// Empty Base

public class GhastlyEssencePower extends AbstractPower {
    public AbstractCreature source;
    public boolean upgraded;

    public static final String POWER_ID = ThiefMod.makeID("GhastlyEssencePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public GhastlyEssencePower(AbstractPlayer owner, AbstractCreature source, boolean upgraded, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = ImageMaster.loadImage(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        this.upgraded = upgraded;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, source,
                        new ShadowstepPower(owner, source, amount), amount));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, source,
                            new ShadowstepPower(owner, source, amount), amount));

        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (upgraded) {
            if (amount == 1) {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
            } else {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[3];
            }
        } else {
            if (amount == 1) {
                description = DESCRIPTIONS[1] + DESCRIPTIONS[2];
            } else {
                description = DESCRIPTIONS[1] + DESCRIPTIONS[3];
            }
        }
    }


}


