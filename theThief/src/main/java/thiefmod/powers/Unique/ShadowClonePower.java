package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.ShadowClone;

import java.util.ArrayList;
import java.util.Collections;


public class ShadowClonePower extends AbstractPower {

    public AbstractCreature source;

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("ShadowClonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);

    public static ArrayList<AbstractCard> playedCards;

    public ShadowClonePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {

        this.name = NAME;
        this.ID = POWER_ID;

        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;


        this.amount = amount;

        this.updateDescription();
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
                copyCard.freeToPlayOnce= true;
                AbstractMonster randomMonster = AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);

                for (int i=0; i<=amount; i++)
                    AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(randomMonster, false, copyCard));

                playedCards.clear();
                break;
            }
        }

    }


    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

}




