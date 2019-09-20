package thiefmod.events;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import thiefmod.ThiefMod;
import thiefmod.relics.LouseBounty;

public class LouseAbuseEvent extends AbstractImageEvent {
    // Gain a unique relic. If you ever FTK a louse - the relic stops glowing and you get a 150g reward.
    
    public static final String ID = ThiefMod.makeID("LouseAbuseEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int HEALTH_LOSS;
    private AbstractRelic relic = null;

    public LouseAbuseEvent() {
        super(NAME, DESCRIPTIONS[0], "theThiefAssets/images/events/LouseAbuseEvent.png");
        
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
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[4]);
                        screenNum = 1;
                        break;
                    case 4:
                        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, HEALTH_LOSS));
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("BLUNT_FAST");
                        imageEventText.clearAllDialogs();
                        imageEventText.setDialogOption(OPTIONS[5]);
                        screenNum = 2;
                        break;
                }
            case 1:
                if (AbstractDungeon.player.hasRelic(LouseBounty.ID)) {
                    relic = RelicLibrary.getRelic(Circlet.ID).makeCopy();
                } else {
                    relic = RelicLibrary.getRelic(LouseBounty.ID).makeCopy();
                }
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), relic);

                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[5]);
                screenNum = 2;
                break;
            case 2:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }
}
