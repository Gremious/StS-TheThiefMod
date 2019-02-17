package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.RetrievalAction;


public class RetrievalPower extends AbstractPower {
    private static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());


    public AbstractCreature source;
    private int returnAmount;
    private int count = 0;

    public static final String POWER_ID = ThiefMod.makeID("RetrievalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public RetrievalPower(final AbstractCreature owner, final AbstractCreature source, final int amount, final int returnAmount) {
        name = NAME;
        ID = POWER_ID;

        img = ImageMaster.loadImage(IMG);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;
        this.returnAmount = returnAmount;

        updateDescription();
    }


    @Override
    public void onAfterCardPlayed(AbstractCard cardPlayed) {
        count++;

        if (count == 3) {
            AbstractDungeon.actionManager.addToBottom(new RetrievalAction(returnAmount));
        }

    }


    @Override
    public void atStartOfTurn() {
        count = 0;
    }

    @Override
    public void updateDescription() {
        if (count == 1) {
            description = DESCRIPTIONS[0] + returnAmount + DESCRIPTIONS[1] + count + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + returnAmount + DESCRIPTIONS[1] + count + DESCRIPTIONS[3];
        }
    }

}




