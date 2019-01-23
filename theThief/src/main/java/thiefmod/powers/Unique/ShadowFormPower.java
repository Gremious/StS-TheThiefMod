package thiefmod.powers.Unique;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.actions.unique.QueueCardFrontAction;
import thiefmod.actions.unique.ShadowFormPlayAction;

import java.util.ArrayList;

public class ShadowFormPower extends AbstractPower {

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("ShadowFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static ArrayList<AbstractCard> cards;
    private static AbstractCard nextCard;
    // have to set isFinishedThisTurn, otherwise starts trying to trigger for cards that we play after playing the power.
    private boolean isFinishedThisTurn = true;
    private int maxAmount;

    public ShadowFormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = this.maxAmount = amount;
        this.isTurnBased = false;
        updateDescription();
        this.loadRegion("hex");
    }

    // Get amount of power stacks and add it to maxAmount.
    @Override
    public void stackPower(final int stackAmount) {
        super.stackPower(stackAmount);
        this.maxAmount += stackAmount;
    }

    @Override
    public void onAfterUseCard(AbstractCard c, UseCardAction action) {
        // if the next card in the queue is the same uuid as the one just played, it's a copy created from Necronomicon/Echo Form.
        // Let it play out immediately, then resume the chain afterwards (when this condition isn't true)
        if ((!AbstractDungeon.actionManager.cardQueue.isEmpty()
                && AbstractDungeon.actionManager.cardQueue.get(0).card.uuid == nextCard.uuid)
                || isFinishedThisTurn)
            return;
        playCardEffect(0);
    }

    // At the start of your turn, if cards is empty, fill it.
    // Regardless, set the amount of cards to play = the maxAmount we set earlier and start the chain.
    @Override
    public void atStartOfTurnPostDraw() {
        if (cards == null) {
            cards = CardLibrary.getAllCards();
        }
        this.amount = this.maxAmount;
        isFinishedThisTurn = false;

        // start the chain
        playCardEffect(0);
    }

    // The big play card effect
    public void playCardEffect(int queueIndex) {
        // If this reaches 0 amount, it's done.
        if (this.amount-- <= 0) {
            this.amount = this.maxAmount;
            isFinishedThisTurn = true;
            return;
        }
        // If it's not however, get some copies of random cards.
        do {
            nextCard = cards.get(AbstractDungeon.cardRandomRng.random(0, cards.size() - 1))
                    .makeCopy();
        } while ((nextCard.rarity == AbstractCard.CardRarity.SPECIAL
                && nextCard.color.toString() != "INFINITE_BLACK")
                || nextCard.type == AbstractCard.CardType.CURSE
                || nextCard.rarity == AbstractCard.CardRarity.CURSE
                || nextCard.type == AbstractCard.CardType.STATUS ||
                // Yohane's summons require a special FriendlyMinions-enabled character, which Beaked is not.
                nextCard.cardID.startsWith("Yohane:Little_Demon_") ||
                // Mad Scientist's Mechanize apparently doesn't work
                nextCard.cardID == "MadScienceMod:Mechanize" ||
                // Pickle why is this ID not public, I'm far too lazy to use reflection
                nextCard.cardID == "ReplayTheSpireMod:??????????????????????" ||
                // blakkmod
                nextCard.cardID == "BlakkBlade" ||
                // blakkmod
                nextCard.cardID == "LegSlice"
        );

        nextCard.purgeOnUse = true;
        nextCard.freeToPlayOnce = true;
        AbstractDungeon.player.limbo.addToTop(nextCard);
        nextCard.target_x = Settings.WIDTH / 2;
        nextCard.target_y = Settings.HEIGHT / 2;
        nextCard.targetDrawScale = nextCard.targetDrawScale * 1.4f;
        AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();

        // Typically, cards are added to the front of the queue so they can't be interrupted by player actions.
        AbstractDungeon.actionManager.addToBottom(new QueueCardFrontAction(nextCard, targetMonster, queueIndex));
        // Wait action can't wait for more than 0.1s on fast mode, so just add a bunch of them
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
        // If a card can't be used, it doesn't trigger onAfterUseCard, which would break the chain.
        // Instead, after the typical wait time, we add the NEXT card to the queue early, placing it after the current unusable card.
        if (!nextCard.canUse(AbstractDungeon.player, targetMonster)) {
            AbstractDungeon.actionManager.addToBottom(new ShadowFormPlayAction(this));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}
