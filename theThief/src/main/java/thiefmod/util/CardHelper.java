package thiefmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;

public class CardHelper {
    public static AbstractCard getRandomColorSpecificCard(AbstractCard.CardColor color, Random rng){
        CardGroup cards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardLibrary.getAllCards()){
            if (c.color == color){
                cards.addToBottom(c);
            }
        }
        return cards.getRandomCard(rng);
    }
}
