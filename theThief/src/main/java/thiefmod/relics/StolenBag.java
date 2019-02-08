package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import thiefmod.patches.character.AbstractCardEnum;

public class StolenBag extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("StolenBag");
    public static final String IMG = "thiefmodAssets/images/relics/StolenBag.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/StolenBag.png";

    private boolean usedThisCombat = false;

    public StolenBag() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void onUseCard(AbstractCard c, UseCardAction useCardAction) {
        if (c.color != AbstractCardEnum.THIEF_GRAY) {

        }
    }

    @Override
    public void atTurnStart() {
        if (!usedThisCombat) {
            beginLongPulse();
        }
    }

    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        beginLongPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
