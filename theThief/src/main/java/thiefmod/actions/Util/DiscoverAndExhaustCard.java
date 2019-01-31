package thiefmod.actions.Util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.patches.Common.DiscoveryColorPatch;

import java.util.ArrayList;

public class DiscoverAndExhaustCard extends AbstractGameAction {
    private ArrayList<AbstractCard> cardList = new ArrayList<>();

    private boolean retrieveCard = false;
    private boolean upgraded;
    private int copies;

    public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:MakeSuperCopyAction");
    public static final String KEYWORD_STRINGS[] = uiStrings.TEXT;

    public DiscoverAndExhaustCard(final ArrayList<AbstractCard> cardList) {
        this(cardList, 3, 1);
    }

    public DiscoverAndExhaustCard(final ArrayList<AbstractCard> cardList, boolean upgraded) {
        this(cardList, upgraded, 3, 1);
    }

    public DiscoverAndExhaustCard(final ArrayList<AbstractCard> cardList, int amount) {
        this(cardList, false, amount, 1);
    }

    public DiscoverAndExhaustCard(final ArrayList<AbstractCard> cardList, int amount, int copies) {
        this(cardList, false, amount, copies);
    }

    public DiscoverAndExhaustCard(final ArrayList<AbstractCard> cardList, boolean upgraded, int amount, int copies) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.copies = copies;
        this.upgraded = upgraded;
        this.cardList = cardList;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {

            if (cardList.size() < amount) {
                amount = cardList.size();
            }

            DiscoveryColorPatch.lookingForCount = amount;
            DiscoveryColorPatch.lookingForUpgraded = upgraded;
            DiscoveryColorPatch.lookingForCardList = cardList;

            AbstractDungeon.cardRewardScreen.discoveryOpen();
            tickDuration();
            return;
        }
        if (!retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard discoveredCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                for (int i = 0; i < copies; i++) {
                    AbstractDungeon.actionManager.actions.add(new MakeSuperCopyAction(discoveredCard, KEYWORD_STRINGS[0], AbstractDungeon.player.hand));
                }

                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }
}
