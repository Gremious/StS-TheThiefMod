package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.actions.util.MakeSuperCopyAction;
import thiefmod.util.TextureLoader;

// Empty Base

public class SimilarSkillsPower extends AbstractPower {


    public static final String POWER_ID = thiefmod.ThiefMod.makeID("SimilarSkillsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/SimilarSkillsPower.png");
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/SimilarSkillsPower.png");


    public SimilarSkillsPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;

        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractCard c;

        for (int i = 0; i < amount; i++) {
            c = CardLibrary.getRandomColorSpecificCard(CardColor.GREEN, AbstractDungeon.cardRandomRng);
            c.modifyCostForCombat(-99);
            AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(c, AbstractDungeon.player.hand));
        }

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }


}




