package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

import java.util.function.Predicate;


public class StolenClockPower extends AbstractPower implements InvisiblePower, NonStackablePower {

    public static final String POWER_ID = ThiefMod.makeID("StolenClockPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/ui/missing_texture.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/ui/missing_texture.png");

    private AbstractCard PatternShift = CardLibrary.getCopy("PatternShift");
    private AbstractPlayer p = AbstractDungeon.player;

    public StolenClockPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.player.hand.contains(PatternShift)) {
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.discardPile, Predicate.isEqual(PatternShift)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.drawPile, Predicate.isEqual(PatternShift)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.exhaustPile, Predicate.isEqual(PatternShift)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.limbo, Predicate.isEqual(PatternShift))); // ? Who knows.

        }
    }

    @Override
    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(PatternShift));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.uuid.equals(PatternShift.uuid)) {
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.discardPile, Predicate.isEqual(card)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.drawPile, Predicate.isEqual(card)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.exhaustPile, Predicate.isEqual(card)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(p.hand, p.limbo, Predicate.isEqual(card))); // ? Who knows.
        }
    }

    /*
    @Override
    public void onRemove()
    {
        if (amount > 1) {
            // Add a copy, only one will be removed
            owner.powers.add(0, this);
            // Cancel the removal text effect
            AbstractDungeon.effectList.remove(AbstractDungeon.effectList.size() - 1);
        }
    }
    */
}


