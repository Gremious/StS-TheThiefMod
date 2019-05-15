package thiefmod.cards.abstracts;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import thiefmod.CardIgnore;

@CardIgnore
public abstract class AbstractStolenCard extends AbstractThiefCard {
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    
    public AbstractStolenCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardTarget target) {
        super(id, img, cost, type, color, CardRarity.SPECIAL, target);
        
    }
    //==
    
    public static String flavortext(String EXTENDED_DESCRIPTION) {
        return EXTENDED_DESCRIPTION;
    }
}