package thiefmod.powers;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TestPower extends AbstractPower {
    public AbstractCreature source;
    public static final String POWER_ID = "TestPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "defaultModResources/images/powers/EB.png";
    public TestPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        type = PowerType.BUFF;
        isTurnBased = false;
        img = ImageMaster.loadImage(IMG);
        this.source = source;
    }

    @Override
    public float atDamageFinalReceive(final float damage, final DamageInfo.DamageType type) {
        return this.calculateDamageTakenAmount(damage, type);
    }


    private float calculateDamageTakenAmount(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.HP_LOSS && type != DamageInfo.DamageType.THORNS && this.amount >= 1) {
            return damage * 0.0f;
        } else if(this.amount <= 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "TestPower"));
            return damage;
        } else {
            return damage;
        }
    }

    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && this.amount >= 1) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "TestPower", 1));
        } else if(this.amount <= 0){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "TestPower"));
        }
        return damageAmount;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}