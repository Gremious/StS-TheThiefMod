package thiefmod.actions.unique;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.MakeSuperCopyAction;

import java.util.ArrayList;

public class StolenArsenalAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private AbstractPlayer player;

    private ArrayList<AbstractCard> drawCards = new ArrayList<>();
    private ArrayList<AbstractCard> discardCards = new ArrayList<>();
    private ArrayList<AbstractCard> exhaustCards = new ArrayList<>();
    private ArrayList<AbstractCard> handCards = new ArrayList<>();

    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;

    public StolenArsenalAction(final AbstractPlayer player) {
        this.player = player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        logger.info("Update start");
        if (this.duration == Settings.ACTION_DUR_FAST) {
            logger.info("Action duration is fast");

            for (AbstractCard c : player.hand.group) {
                AbstractDungeon.player.hand.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Hand"));
            }
            for (AbstractCard c : player.drawPile.group) {
                AbstractDungeon.player.drawPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Draw"));
            }
            for (AbstractCard c : player.discardPile.group) {
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Discard"));
            }

            for (AbstractCard c : player.exhaustPile.group) {
                AbstractDungeon.player.exhaustPile.removeCard(c);
                AbstractDungeon.player.exhaustPile.addToTop(new Shiv());
            }

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();

            logger.info("Did all the for loops, bout to draw cards");

            int maximumHand = BaseMod.MAX_HAND_SIZE;
            int currentHand = player.hand.group.size();

            do {
                if (!AbstractDungeon.player.drawPile.isEmpty() || !AbstractDungeon.player.discardPile.isEmpty()) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player, 1));
                    currentHand++;
                } else break;
            }
            while (currentHand < maximumHand);
            logger.info("drew the cards, gonna end action");
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
            tickDuration();
        }
        tickDuration();
    }
}
