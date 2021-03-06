package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.ShadowClone;
import thiefmod.util.TextureLoader;

import java.util.ArrayList;
import java.util.Collections;


public class ShadowClonePower extends AbstractPower {

    public AbstractCreature source;

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("ShadowClonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/ShadowClonePower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/ShadowClonePower.png");


    public static ArrayList<AbstractCard> playedCards;

    public ShadowClonePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {

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
    public void atStartOfTurnPostDraw() {
        playedCards = AbstractDungeon.actionManager.cardsPlayedThisCombat;

        Collections.reverse(playedCards);

        for (AbstractCard card : playedCards) {
            if (card == null) {
                break;
            } else if (card.cardID.equals(ShadowClone.ID)) {
                continue;
            } else {
                AbstractCard copyCard = card.makeStatEquivalentCopy();
                copyCard.purgeOnUse = true;
                copyCard.freeToPlayOnce = true;

                for (int i = 0; i <= amount; i++)
                    AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(false, copyCard));

                playedCards.clear();
                break;
            }
        }

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




