package thiefmod.powers.Challenges;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

public class AbstractSecretPower extends AbstractPower implements InvisiblePower, NonStackablePower {
    public static final String POWER_ID = ThiefMod.makeID("EmptyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/ui/missing_texture.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/ui/missing_texture.png");
    
    public AbstractSecretPower() {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;
    }
    
    @Override
    public void onRemove() {
        if (amount > 1) {
            // Add a copy, only one will be removed
            owner.powers.add(0, this);
            // Cancel the removal text effect
            AbstractDungeon.effectList.remove(AbstractDungeon.effectList.size() - 1);
        }
    }
}
