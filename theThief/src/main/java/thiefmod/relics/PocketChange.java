package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.MawBank;
import com.megacrit.cardcrawl.relics.SmilingMask;
import thiefmod.powers.Common.RefundCardCost;

public class PocketChange extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("PocketChange");
    public static final String IMG = "defaultModResources/images/relics/PocketChange.png";
    public static final String OUTLINE = "defaultModResources/images/relics/outline/PocketChange.png";

    public PocketChange() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip("Synergy",
                DESCRIPTIONS[1] + FontHelper.colorString(new SmilingMask().name, "y") + DESCRIPTIONS[2]));
        initializeTips();
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player, new RefundCardCost(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + new MawBank().name + ".";
    }

}
