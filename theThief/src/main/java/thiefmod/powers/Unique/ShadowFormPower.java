package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.actions.Util.getRandomCardFromAnyColor;
import thiefmod.util.TextureLoader;

public class ShadowFormPower extends AbstractPower {

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("ShadowFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/ShadowFormPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/ShadowFormPower.png");

    public ShadowFormPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.amount = amount;

        updateDescription();

        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    @Override
    public void atStartOfTurn() {
        getRandomCardFromAnyColor randomCards = new getRandomCardFromAnyColor(3, false, true);

       AbstractDungeon.actionManager.addToBottom(new DiscoverRandomFromArrayAction(randomCards.getListOfRandomCards(), 3));
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
