package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.powers.Common.ElusivePower;
import thiefmod.util.TextureLoader;

// Empty Base

public class ShadowMasteryPower extends AbstractPower implements OnReceivePowerPower {
    public AbstractCreature source;
    
    public static final String POWER_ID = ThiefMod.makeID("ShadowMasteryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/LiarPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/LiarPower.png");
    
    private static boolean usedThisTurn = false;
    
    public ShadowMasteryPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;
        
        this.owner = owner;
        this.source = source;
        
        this.amount = amount;
        
        updateDescription();
    }
    
    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (power.ID.equals(ElusivePower.POWER_ID)) {
            if (!usedThisTurn) {
                power.amount += amount;
                usedThisTurn = true;
            }
        }
        return true;
    }
    
    public void atStartOfTurn() {
        usedThisTurn = false;
    }
    
    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}


