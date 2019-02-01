package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;

// Empty Base

public class SimilarSkillsPowerUpgraded extends AbstractPower {
    private boolean isUpgraded;

    public static final String POWER_ID = thiefmod.ThiefMod.makeID("SimilarSkillsPowerUpgraded");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = ThiefMod.makePath(ThiefMod.COMMON_POWER);


    public SimilarSkillsPowerUpgraded(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        img = ImageMaster.loadImage(IMG);
        type = PowerType.BUFF;
        isTurnBased = false;

        this.owner = owner;
        this.isUpgraded = isUpgraded;

        this.amount = amount;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractCard c;

        for (int i = 0; i < amount; i++) {
            c = CardLibrary
                    .getRandomColorSpecificCard(CardColor.GREEN, AbstractDungeon.cardRandomRng).makeCopy();
            c.upgrade();
            c.modifyCostForCombat(-20);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, amount, true, true));
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




