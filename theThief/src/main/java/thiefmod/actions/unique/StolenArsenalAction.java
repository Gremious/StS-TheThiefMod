package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.actions.Util.MakeSuperCopyAction;

import java.util.ArrayList;

public class StolenArsenalAction extends AbstractGameAction {
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
        if (this.duration == Settings.ACTION_DUR_FAST) {

            handCards.addAll(player.hand.group);
            drawCards.addAll(player.drawPile.group);
            discardCards.addAll(player.discardPile.group);
            exhaustCards.addAll(player.exhaustPile.group);

            int handSize = player.hand.size();
            int drawSize = drawCards.size();
            int discardSize = discardCards.size();
            int exhaustSize = exhaustCards.size();


            for (AbstractCard c : handCards) {
                AbstractDungeon.player.hand.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Hand"));
            }
            for (AbstractCard c : drawCards) {
                AbstractDungeon.player.drawPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Draw"));
            }
            for (AbstractCard c : discardCards) {
                AbstractDungeon.player.discardPile.removeCard(c);
                AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(new Shiv(), KEYWORD_STRINGS[0], true, "Discard"));
            }
            for (AbstractCard c : exhaustCards) {
                AbstractDungeon.player.exhaustPile.removeCard(c);
                AbstractDungeon.player.exhaustPile.addToTop(new Shiv());
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();
        }
        tickDuration();
    }
}
