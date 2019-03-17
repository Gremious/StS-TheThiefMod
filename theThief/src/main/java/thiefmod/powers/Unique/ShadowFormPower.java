package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.actions.Util.getRandomCardFromAnyColor;
import thiefmod.util.TextureLoader;

import java.util.ArrayList;

public class ShadowFormPower extends AbstractPower {

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("ShadowFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/ShadowFormPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/ShadowFormPower.png");

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
        for (int i = 0; i < amount; i++) {
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));

            getRandomCardFromAnyColor randomCards = new getRandomCardFromAnyColor(3);

            ArrayList<AbstractCard> cards = new ArrayList<>(randomCards.getListOfRandomCards());

            for (AbstractCard c : cards) {
                c.modifyCostForCombat(-99);
            }

            AbstractDungeon.actionManager.addToBottom(new DiscoverRandomFromArrayAction(cards, 3));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
