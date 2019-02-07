package thiefmod.events;

import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.JAX;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import thiefmod.ThiefMod;
import thiefmod.relics.LouseBounty;

public class BlackMarketTrader extends AbstractImageEvent {
    // Floor 1 or 2? Probably just 1.
    // Bottled Heart - Whenever you lose HP, return the bottled card to your hand. Tip: Doesn't return from exhaust.


    public static final String ID = ThiefMod.makeID("BlackMarketTrader");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;
    private int HEALTH_LOSS_LOW;
    private int HEALTH_LOSS_MEDIUM;
    private int HEALTH_LOSS_LARGE;

    public BlackMarketTrader() {
        super(NAME, DESCRIPTIONS[0], "thiefmodAssets/images/relics/Lockpicks.png");

        if (AbstractDungeon.ascensionLevel >= 15) {
            HEALTH_LOSS_LOW = 2;
            HEALTH_LOSS_MEDIUM = 4;
            HEALTH_LOSS_LARGE = 7;
        } else {
            HEALTH_LOSS_LOW = 2;
            HEALTH_LOSS_MEDIUM = 4;
            HEALTH_LOSS_LARGE = 7;
        }

        imageEventText.setDialogOption(OPTIONS[0], new JAX());
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0: /*J.A.X.*/
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new JAX(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1: /*Random Upgraded Uncommon.*/
                        AbstractCard c = AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON, AbstractDungeon.cardRng).makeCopy();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2: /*Bottled Heart*/
                        AbstractDungeon.player.relics.add(new LouseBounty()); //TODO
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 3: /*Leave*/
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
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
