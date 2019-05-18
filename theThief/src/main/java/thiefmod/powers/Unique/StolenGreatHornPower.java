package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.bard.actions.common.QueueNoteAction;
import com.evacipated.cardcrawl.mod.bard.notes.WildCardNote;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

public class StolenGreatHornPower extends AbstractPower {
    public AbstractCreature source;
    
    public static final String POWER_ID = thiefmod.ThiefMod.makeID("StolenGreatHornPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    private static final Texture tex84;
    private static final Texture tex32;
    
    static {
        if (ThiefMod.hasBard) {
            tex84 = TextureLoader.getTexture("null");
            tex32 = TextureLoader.getTexture("null");
        } else {
            tex84 = TextureLoader.getTexture("null");
            tex32 = TextureLoader.getTexture("null");
        }
    }
    
    public StolenGreatHornPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 32, 32);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        type = PowerType.BUFF;
        isTurnBased = false;
        
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        
        updateDescription();
    }
    
    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(WildCardNote.get()));
        AbstractDungeon.actionManager.addToBottom(new QueueNoteAction(WildCardNote.get()));
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


