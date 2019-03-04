package thiefmod.hooks;

import com.megacrit.cardcrawl.cards.AbstractCard;

// Interface for the CanUsePower hook - allows powers to pass true/false to AbstractCard.canPlay
// so that powers can prevent you from using cards (like e.g. Normality)
// Used for patches.powersCanStopPlay

public interface CanUsePower {
    boolean canUse(AbstractCard card);
}


