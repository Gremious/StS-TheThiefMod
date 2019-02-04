package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.powers.Common.RefundCardCost;

public class PocketChange extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("PocketChange");
    public static final String IMG = "defaultModResources/images/relics/PocketChange.png";
    public static final String OUTLINE = "defaultModResources/images/relics/outline/PocketChange.png";

    public PocketChange() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RefundCardCost(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
