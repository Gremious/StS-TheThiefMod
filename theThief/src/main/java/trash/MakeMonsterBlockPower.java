package trash;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

import java.lang.reflect.Field;


// Empty Base

public class MakeMonsterBlockPower extends AbstractPower {

    public static final String POWER_ID = ThiefMod.makeID("Empty");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);

    private byte moveByte;
    private Intent moveIntent;
    private EnemyMoveInfo move;

    public MakeMonsterBlockPower(AbstractMonster owner) {
        this(owner, 1);
    }

    public MakeMonsterBlockPower(AbstractMonster owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
    }


    public void atEndOfRound() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

    }


    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (MakeMonsterBlockPower.this.owner instanceof AbstractMonster) {
                    MakeMonsterBlockPower.this.moveByte = ((AbstractMonster) MakeMonsterBlockPower.this.owner).nextMove;
                    MakeMonsterBlockPower.this.moveIntent = ((AbstractMonster) MakeMonsterBlockPower.this.owner).intent;

                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        MakeMonsterBlockPower.this.move = (EnemyMoveInfo) f.get(MakeMonsterBlockPower.this.owner);
                        MakeMonsterBlockPower.this.move.intent = Intent.DEFEND;
                        ((AbstractMonster) MakeMonsterBlockPower.this.owner).createIntent();
                    } catch (NoSuchFieldException | IllegalAccessException var2) {
                        var2.printStackTrace();
                    }
                }

                this.isDone = true;
            }
        });
    }

    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }

            m.createIntent();
            m.applyPowers();
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


