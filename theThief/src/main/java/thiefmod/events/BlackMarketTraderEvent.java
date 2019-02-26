package thiefmod.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thiefmod.ThiefMod;
import thiefmod.relics.BottledHand;
import thiefmod.relics.LouseBounty;

public class BlackMarketTraderEvent extends AbstractImageEvent {


    public static final String ID = ThiefMod.makeID("BlackMarketTraderEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    private float HEALTH_LOSS_PERCENTAGE_LOW = 0.03F;
    private float HEALTH_LOSS_PERCENTAGE_MEDIUM = 0.06F;
    private float HEALTH_LOSS_PERCENTAGE_LARGE = 0.10F;

    private float HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION = 0.05F;
    private float HEALTH_LOSS_PERCENTAGE_MEDIUM_ASCENSION = 0.09F;
    private float HEALTH_LOSS_PERCENTAGE_LARGE_ASCENSION = 0.13F;

    private int damageLow;
    private int damageMedium;
    private int damageHigh;

    public BlackMarketTraderEvent() {
        super(NAME, DESCRIPTIONS[0], "thiefmodAssets/images/events/BlackMarketTraderEvent.png");

        if (AbstractDungeon.ascensionLevel >= 15) {
            damageLow =     (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW);
            damageMedium =  (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW);
            damageHigh =    (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LARGE);

        } else {
            damageLow =     (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LOW_ASCENSION);
            damageMedium =  (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_MEDIUM_ASCENSION);
            damageHigh =    (int) ((float) AbstractDungeon.player.maxHealth * HEALTH_LOSS_PERCENTAGE_LARGE_ASCENSION);

        }

        imageEventText.setDialogOption(OPTIONS[0] + damageLow + OPTIONS[1], new JAX());
        imageEventText.setDialogOption(OPTIONS[2] + damageMedium + OPTIONS[3]);
        imageEventText.setDialogOption(OPTIONS[4] + damageHigh + OPTIONS[5]);
        imageEventText.setDialogOption(OPTIONS[6]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0: /*J.A.X.*/

                        AbstractDungeon.player.decreaseMaxHealth(this.damageLow);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                        CardCrawlGame.sound.play("BLOOD_SPLAT");
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new JAX(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: /*Random Upgraded Uncommon.*/
                        AbstractDungeon.player.decreaseMaxHealth(this.damageMedium);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                        CardCrawlGame.sound.play("BLOOD_SPLAT");

                        AbstractCard c = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON, AbstractDungeon.cardRng).makeCopy();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2: /*Bottled Heart*/
                        AbstractDungeon.player.decreaseMaxHealth(this.damageHigh);
                        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
                        CardCrawlGame.sound.play("ATTACK_DAGGER_6");
                        CardCrawlGame.sound.play("BLOOD_SPLAT");
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new BottledHand().makeCopy());
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 3: /*Leave*/
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[7]);
                        this.imageEventText.clearRemainingOptions();
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
