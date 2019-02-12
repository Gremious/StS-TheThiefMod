package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.actions.common.StealCardAction;
import thiefmod.util.TextureLoader;

public class SharpPracticePower extends AbstractPower {
    private boolean isUpgraded;
    private boolean ADD_UPGRADED;
    private boolean ADD_RANDOM;
    private CardGroup ADD_LOCATION;
    private AbstractPlayer source;

    public static final String POWER_ID = ThiefMod.makeID("SharpPracticePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/SharpPracticePower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/SharpPracticePower.png");

    public SharpPracticePower(final AbstractCreature owner, final AbstractPlayer source, boolean isUpgraded, final int amount, boolean ADD_RANDOM, final CardGroup ADD_LOCATION, boolean ADD_UPGRADED) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.isUpgraded = isUpgraded;

        this.amount = amount;
        this.ADD_UPGRADED = ADD_UPGRADED;
        this.ADD_RANDOM = ADD_RANDOM;
        this.ADD_LOCATION = ADD_LOCATION;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new StealCardAction(
                amount, 1, ADD_RANDOM, ADD_LOCATION, ADD_UPGRADED));
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


