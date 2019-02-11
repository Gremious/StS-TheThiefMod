package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

// Empty Base

public class TheThiefThieveryPower extends AbstractPower {
    private AbstractPlayer source;

    public static final String POWER_ID = ThiefMod.makeID("TheThiefThieveryPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("thiefmodAssets/images/powers/84/ShadowClonePower.png");
    private static final Texture tex32 = TextureLoader.getTexture("thiefmodAssets/images/powers/32/ShadowClonePower.png");


    public TheThiefThieveryPower(final AbstractPlayer source, final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.source = source;
        this.owner = owner;
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature attackTarget) {

        com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gainGold(amount);
        for (int i = 0; i < amount; ++i) {
            AbstractDungeon.effectList.add(new GainPennyEffect(source, attackTarget.hb.cX, attackTarget.hb.cY, source.hb.cX, source.hb.cY, true));
        }
        //    AbstractDungeon.actionManager.addToBottom(new GainGoldAction(owner, source, amount, attackTarget, info));
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, source, ID));
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

    }


}


