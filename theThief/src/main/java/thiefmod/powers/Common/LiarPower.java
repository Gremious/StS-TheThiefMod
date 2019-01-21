package thiefmod.powers.Common;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import thiefmod.ThiefMod;

// Empty Base

public class LiarPower extends AbstractPower {
    public static AbstractCreature source;
    public static AbstractMonster target;
    private static int debuffAmount;
    private static boolean upgraded;

    public static final String POWER_ID = ThiefMod.makeID("LiarPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public LiarPower(AbstractCreature owner, AbstractCreature source, AbstractMonster target, final boolean upgraded, final int amount, final int debuffAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;
        this.target = target;
        this.upgraded = upgraded;

        this.amount = amount;

        this.debuffAmount = debuffAmount;
        this.updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type.equals(AbstractCard.CardType.SKILL)) {
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(target, source, new WeakPower(target, debuffAmount, false), debuffAmount));
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(target, source, new VulnerablePower(target, debuffAmount, false), debuffAmount));
            } else {
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(target, source, new WeakPower(target, debuffAmount, false), debuffAmount));
            }
        }
    }


    public void atEndOfRound() {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
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


