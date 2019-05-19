package thiefmod.actions.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import thiefmod.patches.DiscoveryPatch;

public class DiscoverCardAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    
    private CardGroup cardList;
    private int amount;
    private boolean upgraded;
    private Integer costForTurn;
    private AbstractCard.CardType type;
    private AbstractCard.CardColor color;
    private AbstractCard.CardRarity rarity;
    
    public DiscoverCardAction(final CardGroup cardList) {
        this(cardList, 3, false, null, null, null, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final int amount) {
        this(cardList, amount, false, null, null, null, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final boolean upgraded) {
        this(cardList, 3, upgraded, null, null, null, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final Integer costForTurn) {
        this(cardList, 3, false, costForTurn, null, null, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final AbstractCard.CardType type) {
        this(cardList, 3, false, null, type, null, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final AbstractCard.CardColor color) {
        this(cardList, 3, false, null, null, color, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final AbstractCard.CardRarity rarity) {
        this(cardList, 3, false, null, null, null, rarity);
    }
    
    public DiscoverCardAction(final CardGroup cardList, final AbstractCard.CardType type, final AbstractCard.CardColor color) {
        this(cardList, 3, false, null, type, color, null);
    }
    
    public DiscoverCardAction(final CardGroup cardList,
                              final AbstractCard.CardType type,
                              final AbstractCard.CardColor color,
                              final AbstractCard.CardRarity rarity) {
        this(cardList, 3, false, null, type, color, rarity);
    }
    
    /**
     * @param cardList    Group of cards to discover from
     * @param amount      Amount of cards to discover from
     * @param upgraded    Whether the cards should be upgraded
     * @param costForTurn Sets the cost of the card for the turn. Pass null to keep it unchanged.
     * @param type        The type of cards to discover.
     * @param color       The color to discover.
     * @param rarity      The rarity to discover.
     */
    public DiscoverCardAction(final CardGroup cardList,
                              final int amount,
                              final boolean upgraded,
                              final Integer costForTurn,
                              final AbstractCard.CardType type,
                              final AbstractCard.CardColor color,
                              final AbstractCard.CardRarity rarity) {
        actionType = ActionType.CARD_MANIPULATION;
        duration = Settings.ACTION_DUR_FAST;
        
        this.cardList = cardList;
        this.amount = amount;
        this.upgraded = upgraded;
        this.costForTurn = costForTurn;
        this.type = type;
        this.color = color;
        this.rarity = rarity;
    }
    
    @Override
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (cardList.size() < amount) amount = cardList.size();
            
            if (cardList.size() <= 0) {
                isDone = true;
            }
            
            if (type != null) {
                if (DiscoveryPatch.cardUtil.countTypes(cardList.group, type) <= 0) {
                    isDone = true;
                }
            }
            
            if (color != null) {
                if (DiscoveryPatch.cardUtil.countColor(cardList.group, color) <= 0) {
                    isDone = true;
                }
            }
            
            if (rarity != null) {
                if (DiscoveryPatch.cardUtil.countRarity(cardList.group, rarity) <= 0) {
                    isDone = true;
                }
            }
            
            DiscoveryPatch.customDiscovery = true;
            
            DiscoveryPatch.amount = amount;
            DiscoveryPatch.upgraded = upgraded;
            DiscoveryPatch.cardGroupToDiscoverFrom = cardList;
            DiscoveryPatch.type = type;
            DiscoveryPatch.color = color;
            DiscoveryPatch.rarity = rarity;
            
            AbstractDungeon.cardRewardScreen.discoveryOpen();
            tickDuration();
            return;
        }
        
        if (!retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                disCard.current_x = -1000.0f * Settings.scale;
                if (costForTurn != null) disCard.setCostForTurn(costForTurn);
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
