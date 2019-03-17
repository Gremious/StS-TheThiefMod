package thiefmod.powers.Unique;

import blackrusemod.actions.ThrowKnivesAction;
import blackrusemod.powers.DeadlinePower;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

// Empty Base

public class StolenKnivesPower extends AbstractPower {

    public int damage;
    public DamageInfo info;

    public static final String POWER_ID = ThiefMod.makeID("StolenKnivesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/StolenCorePower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/StolenCorePower.png");

    public StolenKnivesPower(final AbstractCreature owner, final int amount, int damage, DamageInfo info) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.amount = amount;
        this.damage = damage;
        this.info = info;

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        for (int i = 0; i < amount; i++) {
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.monsterRng);
            AbstractDungeon.actionManager.addToBottom(new ThrowKnivesAction(AbstractDungeon.player, randomMonster, info, DeadlinePower.POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + damage + DESCRIPTIONS[5];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[4] + damage + DESCRIPTIONS[5];
        }
    }
}


