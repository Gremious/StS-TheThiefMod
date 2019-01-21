package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AutoplayCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.actions.common.GainGoldAction;
import thiefmod.cards.ShadowClone;
import thiefmod.patches.Unique.ThiefCardTags;

// Empty Base

public class ShadowClonePower extends AbstractPower {
    public AbstractCreature source;
    public AbstractPlayer owner;

    public static final String POWER_ID = ThiefMod.makeID("ShadowClonePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public ShadowClonePower(AbstractPlayer owner, AbstractCreature source, final int amount) {
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
        for (AbstractCard card : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (card.cardID.equals(ShadowClone.ID)) {
                return;
            } else {
                AbstractCard cardCopy = card.makeSameInstanceOf();
                AbstractMonster target = AbstractDungeon.getRandomMonster();

                cardCopy.purgeOnUse = true;
                cardCopy.freeToPlayOnce = true;

                AbstractDungeon.actionManager.addToTop(new QueueCardAction(cardCopy, target));
                break;
            }
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(ThiefCardTags.STEALING)) {
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(this.owner, this.source, this.amount));
        } else {
            return;
        }
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }


}


