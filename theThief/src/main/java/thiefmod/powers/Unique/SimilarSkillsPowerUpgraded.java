package thiefmod.powers.Unique;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
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
        this.name = NAME;
        this.ID = POWER_ID;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.owner = owner;
        this.isUpgraded = isUpgraded;

        this.amount = amount;

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractCard c;

        for (int i = 0; i < this.amount; i++) {
            c = CardLibrary
                    .getRandomColorSpecificCard(CardColor.GREEN, AbstractDungeon.cardRandomRng).makeCopy();
            c.upgrade();
            c.modifyCostForCombat(-20);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, this.amount, true, true));
        }

        this.updateDescription();
    }


    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }


}




