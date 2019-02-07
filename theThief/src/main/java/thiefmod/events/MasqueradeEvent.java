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
                        imageEventText.loadImage("thiefmodAssets/images/relics/LoadedDice.png");
                        imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        imageEventText.updateDialogOption(0, OPTIONS[7]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;
                    case 2: /*You press [Steal]*/
                        imageEventText.loadImage("thiefmodAssets/images/relics/LoadedDice.png");
                        imageEventText.updateBodyText(DESCRIPTIONS[9]);
                        imageEventText.updateDialogOption(0, OPTIONS[11]);
                        imageEventText.updateDialogOption(1, OPTIONS[13]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 5;
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
            case 2:/*Flirt*/
                imageEventText.updateBodyText(DESCRIPTIONS[5]);
                imageEventText.updateDialogOption(0, OPTIONS[6]);
                imageEventText.updateDialogOption(1, OPTIONS[9]);
                imageEventText.clearRemainingOptions();
                screenNum = 3;
                break;
            case 3:/*Flirt 2.*/
                switch (i) {
                    case 0: /*Flirt - Continue*/
                        imageEventText.updateBodyText(DESCRIPTIONS[6]);
                        imageEventText.updateDialogOption(0, OPTIONS[7]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 4;
                        break;
                    case 1:/*Flirt - Ignore*/
                        // ADD A DEDICATION
                        imageEventText.updateBodyText(DESCRIPTIONS[8]);
                        imageEventText.updateDialogOption(0, OPTIONS[10]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 4: /*Flirt Final*/
                // ADD A SHAME.
                imageEventText.updateBodyText(DESCRIPTIONS[7]);
                imageEventText.updateDialogOption(0, OPTIONS[8]);
                imageEventText.clearRemainingOptions();
                screenNum = -1;
                break;
            case 5:/*Steal*/
                switch (i) {
                    case 0: /*Steal - Steal*/
                        imageEventText.updateBodyText(DESCRIPTIONS[10]);
                        imageEventText.updateDialogOption(0, OPTIONS[12]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                    case 1:/*Steal - Conversation*/
                        // Gain 75 gold
                        imageEventText.updateBodyText(DESCRIPTIONS[11]);
                        imageEventText.updateDialogOption(0, OPTIONS[14]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 6:/*???*/
                imageEventText.updateBodyText(DESCRIPTIONS[5]);
                imageEventText.updateDialogOption(0, OPTIONS[6]);
                imageEventText.updateDialogOption(1, OPTIONS[9]);
                imageEventText.clearRemainingOptions();
                screenNum = -1;
                break;
            case 7:/*???*/
                imageEventText.updateBodyText(DESCRIPTIONS[5]);
                imageEventText.updateDialogOption(0, OPTIONS[6]);
                imageEventText.updateDialogOption(1, OPTIONS[9]);
                imageEventText.clearRemainingOptions();
                screenNum = -1;
                break;
            case -1:
                if (i == 0) {
                    openMap();
                }
        }
    }


    // [Drink Wine.]
    // You decide to relax a bit and lean back again the table with the fruity-juice bowl.
    // Heal 15% max hp. Add a Drunk curse to your deck.
    // Drunk - When you draw this card, shuffle 2 "Dazed" into your draw and discard piles. Exhaustive 2.
}