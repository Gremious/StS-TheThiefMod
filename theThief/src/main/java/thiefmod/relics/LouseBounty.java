package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.LouseDefensive;
import com.megacrit.cardcrawl.monsters.exordium.LouseNormal;
import thiefmod.actions.common.GainGoldAction;

public class LouseBounty extends CustomRelic {

    public static final String ID = thiefmod.ThiefMod.makeID("LouseBounty");
    public static final String IMG = "thiefmodAssets/images/relics/LouseBounty.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/LouseBounty.png";

    private static boolean used = false;

    public LouseBounty() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);

        tips.clear();
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        ++this.counter;
        if (this.counter != 1) {
            stopPulse();
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (m.id.equals(LouseNormal.ID) || m.id.equals(LouseDefensive.ID) && !used && counter <= 1) {
            used = true;
            AbstractDungeon.actionManager.addToBottom(new GainGoldAction(AbstractDungeon.player, AbstractDungeon.player, 100));
            stopPulse();
        }
    }

    @Override
    public void atPreBattle() {
        if (!used) {
            beginLongPulse();
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
