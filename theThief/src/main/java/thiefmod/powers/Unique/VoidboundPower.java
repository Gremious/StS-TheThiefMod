package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

// Empty Base

public class VoidboundPower extends AbstractPower implements OnCardDrawPower {
    public AbstractCreature source;

    public static final String POWER_ID = ThiefMod.makeID("VoidboundPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public VoidboundPower(AbstractPlayer owner, AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.source = source;

        this.amount = amount;

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {

    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.cardID.equals(VoidCard.ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}


