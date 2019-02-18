package thiefmod.actions.Util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;

import java.util.ArrayList;

public class getRandomCardFromAnyColor {
    // Blatantly stolen (with permission) from the Beaked's Crazy rituals. Thank you very much guys!
    public static int amount;
    public static boolean freeToPlayOnce;
    public static boolean freeThisCombat;

    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    private static ArrayList<AbstractCard> allCards;
    private static AbstractCard oneCard;

    public getRandomCardFromAnyColor(int amount, boolean freeToPlayOnce) {
        this(amount, freeToPlayOnce, false);
    }

    public getRandomCardFromAnyColor(int amount, boolean freeToPlayOnce, boolean freeThisCombat) {
        this.amount = amount;
        this.freeToPlayOnce = freeToPlayOnce;
        this.freeThisCombat = freeThisCombat;
    }

    public static ArrayList<AbstractCard> getRandomCard() {
        ArrayList<AbstractCard> handCards = new ArrayList<>();

        oneCard.freeToPlayOnce = freeToPlayOnce;

        if (freeThisCombat) {
            if (oneCard.cost != -2 && oneCard.cost != -1) {
                oneCard.cost = 0;
            }
        }

        for (int i = 0; i < amount; i++) {
            handCards.add(generateRandomCard());
        }

        return handCards;
    }

    public static AbstractCard generateRandomCard() {

        if (allCards == null) {
            allCards = CardLibrary.getAllCards();
        }

        do {
            oneCard = allCards.get(AbstractDungeon.cardRandomRng.random(0, allCards.size() - 1)).makeCopy();
        } while ((oneCard.rarity == AbstractCard.CardRarity.SPECIAL
                && oneCard.color.toString() != "INFINITE_BLACK")
                || oneCard.type == AbstractCard.CardType.CURSE
                || oneCard.rarity == AbstractCard.CardRarity.CURSE
                || oneCard.type == AbstractCard.CardType.STATUS ||
                // Yohane's summons require a special FriendlyMinions-enabled character.
                oneCard.cardID.startsWith("Yohane:Little_Demon_") ||
                // Mad Scientist's Mechanize apparently doesn't work
                oneCard.cardID == "MadScienceMod:Mechanize" ||
                // Pickle why is this ID not public, I'm far too lazy to use reflection - Beaked
                // So am I - Grem
                oneCard.cardID == "ReplayTheSpireMod:??????????????????????" ||
                // blakkmod
                oneCard.cardID == "BlakkBlade" ||
                // blakkmod
                oneCard.cardID == "LegSlice"

        );
        return oneCard;
    }

}