package thiefmod.actions.Util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import thiefmod.patches.Common.DiscoveryColorPatch;

import java.util.ArrayList;

public class DiscoverRandomFromArrayAction extends AbstractGameAction {
    private ArrayList<AbstractCard> cardList = new ArrayList<>();

    private boolean retrieveCard = false;
    private boolean upgraded;

    public DiscoverRandomFromArrayAction(final ArrayList<AbstractCard> cardList) {
        this(cardList, 3);
    }

    public DiscoverRandomFromArrayAction(final ArrayList<AbstractCard> cardList, int amount) {
        this(cardList, false, amount);
    }

    public DiscoverRandomFromArrayAction(final ArrayList<AbstractCard> cardList, boolean upgraded, int amount) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.upgraded = upgraded;
        this.cardList = cardList;
    }

    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {

            if (cardList.size() < amount)
            {
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
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                disCard.current_x = -1000.0f * Settings.scale;
                if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            retrieveCard = true;
        }
        tickDuration();
    }
}
