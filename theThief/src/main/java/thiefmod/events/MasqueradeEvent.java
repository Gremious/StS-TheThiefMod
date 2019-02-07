package thiefmod.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import thiefmod.ThiefMod;

public class MasqueradeEvent extends AbstractImageEvent {
    public static final String ID = ThiefMod.makeID("MasqueradeEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public MasqueradeEvent() {
        super(NAME, DESCRIPTIONS[0], "thiefmodAssets/images/relics/Lockpicks.png");

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0: /*First Screen. You find the event.*/
                switch (i) {
                    case 0: /*You press [Gossip]*/
                        imageEventText.loadImage("thiefmodAssets/images/relics/LoadedDice.png");
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.updateDialogOption(0, OPTIONS[1]);
                        imageEventText.updateDialogOption(1, OPTIONS[3]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: /*You press [Flirt]*/
                        break;
                    case 2: /*You press [Steal]*/
                        break;
                    case 3: /*You press [Drink Wine]*/
                        break;
                }
                break;
            case 1: /*Gossip*/
                switch (i) {
                    case 0: /*Gossip - Join In*/
                        // ADD 'HOT GOSSIP'
                        imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        imageEventText.updateDialogOption(0, OPTIONS[2]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                    case 1:/*Gossip - Keep Listening*/
                        // ADD A DOUBT
                        imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        imageEventText.updateDialogOption(0, OPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 3:
                break;
            case 4:
                break;
            case -1:
                if (i == 0) {
                    openMap();
                }
        }
    }

    // [Steal.]
    // You notice a creature in a black suit, heavily engaged in conversation. He's absolutely not paying attention to his pockets, but taking something could still be risky.
    // [Steal.]
    // You attempt to sneakily pull something out of his pocket...but you are caught by a guard patroling the ballroom! ADD 1 NORMALITY.
    // [Engage in conversation.]
    // If you have mod installed - He's really engaged in talking about money...this is a spire investor! After his friend goes away, you ask him if he help you get started, and he's very eager to help out. Get 1 copy of spire co stock card.
    // If you don't, just get gold.

    // [Drink Wine.]
    // You decide to relax a bit and lean back again the table with the fruity-juice bowl.
    // Heal 15% max hp. Add a Drunk curse to your deck.
    // Drunk - When you draw this card, shuffle 2 "Dazed" into your draw and discard piles. Exhaustive 2.
}