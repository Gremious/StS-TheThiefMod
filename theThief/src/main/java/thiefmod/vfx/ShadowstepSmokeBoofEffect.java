package thiefmod.vfx;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;


public class ShadowstepSmokeBoofEffect extends SmokeBombEffect {

    protected float x;
    protected float y;

    public ShadowstepSmokeBoofEffect(float x, float y) {
        super(x, y);

        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (duration == 0.2f) {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");
            for (int i = 0; i < 30; ++i) {
                AbstractDungeon.effectsQueue.add(new ShadowstepSingleSmokeEffect(x, y));
            }
        }
        duration -= Gdx.graphics.getDeltaTime();
        if (duration < 0.0f) {
            CardCrawlGame.sound.play("APPEAR");
            isDone = true;
        }
    }
}
