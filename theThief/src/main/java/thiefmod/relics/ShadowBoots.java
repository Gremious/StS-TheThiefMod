package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;

public class ShadowBoots extends CustomRelic {

    /*
    At the start of your turn gain 1 energy and shuffle a void into your discard pile
    thanks robbo!
    */

    public static final String ID = thiefmod.ThiefMod.makeID("ShadowBoots");
    public static final String IMG = "theThiefAssets/images/relics/ShadowBoots.png";
    public static final String OUTLINE = "theThiefAssets/images/relics/outline/ShadowBoots.png";


    public ShadowBoots() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.MAGICAL);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), 1));
    }


    @Override
    public void atBattleStartPreDraw() {
        flash();
    }


    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
