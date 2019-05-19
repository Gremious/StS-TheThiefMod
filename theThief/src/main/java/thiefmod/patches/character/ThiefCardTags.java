package thiefmod.patches.character;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ThiefCardTags {
    @SpireEnum
    public static AbstractCard.CardTags BACKSTAB; // This card is a Backstab card.
    public static AbstractCard.CardTags SHADOWSTEP; // This card is a Shadowstep card.
    public static AbstractCard.CardTags STEALING; // This card is a card that steals cards.
    public static AbstractCard.CardTags STOLEN; // This card is a stolen card.
    public static AbstractCard.CardTags RARE_FIND; // This card is a rare stolen card.
    public static AbstractCard.CardTags MYSTIC_SPELL_TOOLTIP; // This card should display the Mystic Mod "spell" keyword tooltip
    public static AbstractCard.CardTags MYSTIC_ARTE_TOOLTIP; //  This card should display the Mystic Mod "arte" keyword tooltip
}
