package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thiefmod.ThiefMod;
import thiefmod.patches.character.AbstractCardEnum;

// Empty Base

public class FindersKeepersPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = ThiefMod.makeID("FindersKeepersPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public FindersKeepersPower(AbstractCreature owner, AbstractCreature source, final int amount) {
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
        if (!card.color.equals(AbstractCardEnum.THIEF_GRAY)
                && !card.type.equals(AbstractCard.CardType.CURSE)
                && !card.type.equals(AbstractCard.CardType.STATUS)) {

            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

            if (amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, ID, 1));
            }
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


