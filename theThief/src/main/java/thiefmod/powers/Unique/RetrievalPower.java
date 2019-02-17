package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.RetrievalAction;
import thiefmod.util.TextureLoader;


public class RetrievalPower extends AbstractPower {
    private static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());


    public AbstractCreature source;
    private int returnAmount;
    private int count = 0;

    public static final String POWER_ID = ThiefMod.makeID("RetrievalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/RetrievalPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/RetrievalPower.png");


    public RetrievalPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int returnAmount) {
        name = NAME;
        ID = POWER_ID;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;
        this.returnAmount = returnAmount;

        updateDescription();
    }


    @Override
    public void onAfterCardPlayed(AbstractCard cardPlayed) {
        count++;
        updateDescription();
        if (count == 3) {
            AbstractDungeon.actionManager.addToBottom(new RetrievalAction(returnAmount));
        }

    }


    @Override
    public void atStartOfTurn() {
        count = 0;
    }

    @Override
    public void updateDescription() {
        if (count == 1) {
            description = DESCRIPTIONS[0] + returnAmount + DESCRIPTIONS[1] + count + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + returnAmount + DESCRIPTIONS[1] + count + DESCRIPTIONS[3];
        }
    }

}




