package thiefmod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.patches.BottledLiverField;

import java.util.function.Predicate;

public class BottledLiver extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
    private AbstractCard card;

    public static final String ID = thiefmod.ThiefMod.makeID("BottledLiver");
    public static final String IMG = "thiefmodAssets/images/relics/PocketChange.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/PocketChange.png";

    public BottledLiver() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, Predicate.isEqual(card)));
            AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, Predicate.isEqual(card)));
        }
        return damageAmount;
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


    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(this.card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledLiverField.inBottledLiverField::get;
    }
}
