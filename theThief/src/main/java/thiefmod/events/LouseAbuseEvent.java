package thiefmod.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import thiefmod.ThiefMod;
import thiefmod.relics.LouseBounty;
import thiefmod.relics.ThievesMask;

public class LouseAbuseEvent extends AbstractImageEvent {
    // Gain a unique relic. If you ever FTK a louse - the relic stops glowing and you get a 150g reward.

    public static final String ID = ThiefMod.makeID("LouseAbuseEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int HEALTH_LOSS;

    public LouseAbuseEvent() {
        super(NAME, DESCRIPTIONS[0], "thiefmodAssets/images/relics/Lockpicks.png");

        if (AbstractDungeon.ascensionLevel >= 15) {
            HEALTH_LOSS = 3;
        } else {
            HEALTH_LOSS = 5;
        }

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        AbstractDungeon.player.relics.add(new LouseBounty());
                        imageEventText.loadImage("thiefmodAssets/images/relics/LoadedDice.png");
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.updateDialogOption(0, OPTIONS[4]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 4:
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, HEALTH_LOSS));
                        imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        imageEventText.updateDialogOption(0, OPTIONS[8]);
                        imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                if (i == 0) {
                    openMap();
                }
        }
    }
}
