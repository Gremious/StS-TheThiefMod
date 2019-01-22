package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.RetrievalAction;

import java.util.ArrayList;
import java.util.Collections;


public class RetrievalPower extends AbstractPower {
    private static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private ArrayList<AbstractCard> lastCardsPlayed = new ArrayList<>();

    public AbstractCreature source;
    private int returnAmount;
    private int count = 0;

    public static final String POWER_ID = ThiefMod.makeID("RetrievalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public RetrievalPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int returnAmount) {

        this.name = NAME;
        this.ID = POWER_ID;

        this.img = ImageMaster.loadImage(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;


        this.amount = amount;
        this.returnAmount = returnAmount;

        this.updateDescription();
    }


    @Override
    public void onAfterCardPlayed(AbstractCard cardPlayed) {
        logger.info("Ayo a " + cardPlayed + " was just played." +
                " Currently, count is " + count + " and the whole list is " + lastCardsPlayed);
        count++;
        lastCardsPlayed.add(cardPlayed);

        logger.info("Update: the card that was played is still " + cardPlayed +
                "Count++ is " + count + " and the whole list is " + lastCardsPlayed);

        if (count == 3) {
            logger.info("Count is 3, do your thing.");
            Collections.reverse(lastCardsPlayed);
            logger.info("lastCardsPlayed reversed is: " + lastCardsPlayed);
            AbstractDungeon.actionManager.addToBottom(new RetrievalAction(lastCardsPlayed));
            logger.info("Action completed.");

        }

    }

    @Override
    public void atStartOfTurn() {
        lastCardsPlayed.clear();
    }
        /*
        logger.info("onAfterCardPlayedStart");
        lastCardsPlayed.add(cardPlayed);

        Collections.reverse(lastCardsPlayed);

        logger.info("Currently, lastCardsPlayed contains: " + lastCardsPlayed);
        logger.info("Currently, there have been " + lastCardsPlayed.size() + " cards played");

        for (AbstractCard listCard : lastCardsPlayed) {

            logger.info(listCard);

            for (AbstractCard cardCheckDiscard : AbstractDungeon.player.discardPile.group) {

                logger.info("If loop checking if : " + cardCheckDiscard.uuid + " == " + listCard.uuid);
                if (cardCheckDiscard.uuid == listCard.uuid) {

                    logger.info("It is! Add " + listCard + " to hand");

                    AbstractDungeon.actionManager.addToBottom(new DiscardToHandAction(listCard));

                    // AbstractDungeon.player.discardPile.addToHand(listCard); - Doesn't work.

                    logger.info("remove listCard from lastCardsPlayed and then break.");
                    break;
                }
            }
        }
*/


    /* @Override
     public void onPlayCard(AbstractCard onPlayedCard, AbstractMonster onPlayedMonster) {
         this.updateDescription();

             playedCards = AbstractDungeon.actionManager.cardsPlayedThisCombat;
             Collections.reverse(playedCards);

             for (int i = 0; i < returnAmount; i++) {
                 for (AbstractCard card : playedCards) {
                     if (card == null) {
                         break;
                     } else {

                         for (AbstractCard cardCheckDraw : AbstractDungeon.player.drawPile.group)
                             if (cardCheckDraw.uuid == card.uuid) {
                                 AbstractDungeon.player.drawPile.addToHand(card);
                                 playedCards.remove(card);
                                 break;
                             }

                         for (AbstractCard cardCheckDraw : AbstractDungeon.player.discardPile.group)
                             if (cardCheckDraw.uuid == card.uuid) {
                                 AbstractDungeon.player.discardPile.addToHand(card);
                                 playedCards.remove(card);
                                 break;
                             }

                         for (AbstractCard cardCheckDraw : AbstractDungeon.player.exhaustPile.group)
                             if (cardCheckDraw.uuid == card.uuid) {
                                 AbstractDungeon.player.exhaustPile.addToHand(card);
                                 playedCards.remove(card);
                                 break;
                             }
                     }
                     break;
                 }
                 playedCards.clear();

                 this.updateDescription();
             }

     }
 */
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];

    }

}




