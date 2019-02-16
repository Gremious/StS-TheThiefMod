package thiefmod.actions.unique;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.MakeSuperCopyAction;

import java.util.ArrayList;

public class StolenArsenalAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private AbstractPlayer player;

    private final ArrayList<AbstractCard> drawCards = new ArrayList<>();
    private final ArrayList<AbstractCard> discardCards = new ArrayList<>();
    private final ArrayList<AbstractCard> exhaustCards = new ArrayList<>();
    private final ArrayList<AbstractCard> handCards = new ArrayList<>();

    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;

    public StolenArsenalAction(final AbstractPlayer player) {
        this.player = player;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        logger.info("Update start");
        if (duration == Settings.ACTION_DUR_FAST) {
            logger.info("Action duration is fast");

            int maximumHand = BaseMod.MAX_HAND_SIZE;
            int currentHand = AbstractDungeon.player.hand.group.size();

            handCards.addAll(player.hand.group);
            drawCards.addAll(player.drawPile.group);
            discardCards.addAll(player.discardPile.group);
            exhaustCards.addAll(player.exhaustPile.group);

            AbstractDungeon.effectList.add(new BorderFlashEffect(Color.GREEN));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("TINGSHA"));

            for (AbstractCard c : handCards) {
                AbstractDungeon.effectList.add(new ExhaustEmberEffect(c.current_x, c.current_y));
                AbstractDungeon.effectList.add(new CardFlashVfx(c, Color.GOLD));

                AbstractDungeon.player.hand.removeCard(c);
                AbstractDungeon.actionManager.addToTop(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, AbstractDungeon.player.hand));
            }
            for (AbstractCard c : drawCards) {
                AbstractDungeon.player.drawPile.removeCard(c);
                AbstractDungeon.actionManager.addToTop(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, AbstractDungeon.player.drawPile));
            }
            for (AbstractCard c : discardCards) {
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.actionManager.addToTop(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, AbstractDungeon.player.discardPile));
            }

            for (AbstractCard c : exhaustCards) {
                AbstractDungeon.player.exhaustPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new SFXAction("CARD_OBTAIN"));
                AbstractDungeon.player.exhaustPile.addToTop(new Shiv());
            }


            do {
                AbstractDungeon.actionManager.addToTop(new DrawCardAction(player, 1));
                if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
                    break;
                }
                currentHand++;
            }
            while (currentHand != maximumHand);

            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();

            tickDuration();
        }
        tickDuration();
    }
}
