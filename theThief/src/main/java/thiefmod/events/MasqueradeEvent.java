package thiefmod.events;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.curses.Shame;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thiefmod.ThiefMod;
import thiefmod.actions.common.GainGoldAction;
import thiefmod.cards.colorless.Dedication;
import thiefmod.cards.colorless.HotGossip;
import thiefmod.cards.curses.Drunk;

public class MasqueradeEvent extends AbstractImageEvent {
    public static final String ID = ThiefMod.makeID("MasqueradeEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private float HEALTH_HEAL_PERCENT = 0.13F;
    private float HEALTH_HEAL_PERCENT_ASCENSION = 0.10F;

    private int heal;

    private int screenNum = 0;

    public MasqueradeEvent() {
        super(NAME, DESCRIPTIONS[0], "thiefmodAssets/images/events/MasqueradeEvent.png");

        if (AbstractDungeon.ascensionLevel >= 15) {
            heal = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_HEAL_PERCENT);
        } else {
            heal = (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_HEAL_PERCENT_ASCENSION);
        }

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[5]);
        imageEventText.setDialogOption(OPTIONS[11]);
        imageEventText.setDialogOption(OPTIONS[15]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0: /*First Screen. You find the event.*/
                switch (i) {
                    case 0: /*You press [Gossip]*/

                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.updateDialogOption(0, OPTIONS[1]);
                        imageEventText.updateDialogOption(1, OPTIONS[3]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: /*You press [Flirt]*/

                        imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        imageEventText.updateDialogOption(0, OPTIONS[7]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 2;
                        break;
                    case 2: /*You press [Steal]*/

                        imageEventText.updateBodyText(DESCRIPTIONS[9]);
                        imageEventText.updateDialogOption(0, OPTIONS[11]);
                        imageEventText.updateDialogOption(1, OPTIONS[13]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 5;
                        break;
                    case 3: /*You press [Drink Wine]*/

                        imageEventText.updateBodyText(DESCRIPTIONS[12]);
                        imageEventText.updateDialogOption(0, OPTIONS[16] + heal + OPTIONS[17]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 6;
                        break;
                }
                break;
            case 1: /*Gossip*/
                switch (i) {
                    case 0: /*Gossip - Join In*/
                        // ADD 'HOT GOSSIP'
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new HotGossip(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        imageEventText.updateDialogOption(0, OPTIONS[2]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                    case 1:/*Gossip - Keep Listening*/
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Doubt(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        imageEventText.updateDialogOption(0, OPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 2:/*Flirt*/
                imageEventText.updateBodyText(DESCRIPTIONS[5]);
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[6]);
                imageEventText.setDialogOption(OPTIONS[9]);
                // nice.
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
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Dedication(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        imageEventText.updateBodyText(DESCRIPTIONS[8]);
                        imageEventText.updateDialogOption(0, OPTIONS[10]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 4: /*Flirt Final*/
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Shame(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                imageEventText.updateBodyText(DESCRIPTIONS[7]);
                imageEventText.updateDialogOption(0, OPTIONS[8]);
                imageEventText.clearRemainingOptions();
                screenNum = -1;
                break;
            case 5:/*Steal*/
                switch (i) {
                    case 0: /*Steal - Steal*/
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        imageEventText.updateBodyText(DESCRIPTIONS[10]);
                        imageEventText.updateDialogOption(0, OPTIONS[12]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                    case 1:/*Steal - Conversation*/
                        AbstractDungeon.actionManager.addToBottom(new GainGoldAction(AbstractDungeon.player, AbstractDungeon.player, 75));
                        imageEventText.updateBodyText(DESCRIPTIONS[11]);
                        imageEventText.updateDialogOption(0, OPTIONS[14]);
                        imageEventText.clearRemainingOptions();
                        screenNum = -1;
                        break;
                }
                break;
            case 6:/*Drink*/
                // HEAL PLAYER
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, heal));
                // Drunk
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Drunk(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                imageEventText.updateBodyText(DESCRIPTIONS[13]);
                imageEventText.updateDialogOption(0, OPTIONS[18]);
                imageEventText.clearRemainingOptions();
                screenNum = -1;
                break;
            case -1:
                if (i == 0) {
                    openMap();
                    break;
                }
        }
    }
}