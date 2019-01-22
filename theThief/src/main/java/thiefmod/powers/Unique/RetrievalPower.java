package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

import java.util.ArrayList;
import java.util.Collections;


public class RetrievalPower extends AbstractPower {

    public AbstractCreature source;
    public int returnAmount;
    static int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

    public static final String POWER_ID = ThiefMod.makeID("RetrievalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);

    public static ArrayList<AbstractCard> playedCards;

    public RetrievalPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int returnAmount) {

        this.name = NAME;
        this.ID = POWER_ID;

        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;


        this.amount = amount;
        this.returnAmount = returnAmount;

        this.updateDescription();
    }

    @Override
    public void onPlayCard(AbstractCard onPlayedCard, AbstractMonster onPlayedMonster) {
        this.updateDescription();
        if (count == 3) {

            playedCards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
            Collections.reverse(playedCards);

            for (int i = 0; i < returnAmount; i++) {
                for (AbstractCard card : playedCards) {
                    if (card == null) {
                        break;
                    } else {

                        AbstractDungeon.player.hand.addToHand(card);
                        playedCards.remove(card);

                    }
                    break;
                }
                playedCards.clear();

                this.updateDescription();
            }
        }
    }


    @Override
    public void updateDescription() {
        if (count <3)
            this.description = DESCRIPTIONS[0] + returnAmount + DESCRIPTIONS[1] + count + DESCRIPTIONS[2];
        else{
            this.description = DESCRIPTIONS[3] + returnAmount + DESCRIPTIONS[4];
        }
    }

}




