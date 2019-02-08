package thiefmod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import replayTheSpire.patches.BottlePatches;
import thiefmod.patches.relics.BottledLiverField;

import java.util.function.Predicate;

public class BottledLiver extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
    private static AbstractCard card;
    private boolean cardSelected = true;

    public static final String ID = thiefmod.ThiefMod.makeID("BottledLiver");
    public static final String IMG = "thiefmodAssets/images/relics/PocketChange.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/PocketChange.png";

    public BottledLiver() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledLiverField.inBottledLiverField::get;
    }

    @Override
    public Class<Integer> savedType() {
        return Integer.class;
    }

    @Override
    public Integer onSave() {
        if (this.card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(this.card);
        } else {
            return -1;
        }
    }


    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledLiverField.inBottledLiverField.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup group = CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck);
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name + ".", false, false, false, false);
    }


    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledLiverField.inBottledLiverField.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledLiverField.inBottledLiverField.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.setDescriptionAfterLoading();
        }
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            // It doesn't work rn.
            AbstractDungeon.actionManager.addToTop(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, Predicate.isEqual(BottlePatches.BottleFields.inBottleFlurry.get(card))));
            AbstractDungeon.actionManager.addToTop(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, Predicate.isEqual(BottlePatches.BottleFields.inBottleFlurry.get(card))));
        }
        return damageAmount;
    }

    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


}
