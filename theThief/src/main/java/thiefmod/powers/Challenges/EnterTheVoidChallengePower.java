package thiefmod.powers.Challenges;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thiefmod.ThiefMod;
import thiefmod.util.TextureLoader;

import static thiefmod.ThiefMod.isCustomModActive;

public class EnterTheVoidChallengePower extends AbstractPower {
    
    public static final String POWER_ID = ThiefMod.makeID("EnterTheVoidChallengePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static int amount = 1;
    private static final Texture tex32 = TextureLoader.getTexture("theThiefAssets/images/powers/32/VoidboundPower.png");
    private static final Texture tex84 = TextureLoader.getTexture("theThiefAssets/images/powers/84/VoidboundPower.png");
    
    private AbstractPlayer p = AbstractDungeon.player;
    
    public EnterTheVoidChallengePower() {
        this.owner = AbstractDungeon.player;
        name = NAME;
        ID = POWER_ID;
        region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        type = PowerType.BUFF;
        isTurnBased = false;
        updateDescription();
        if (isCustomModActive("challengethespire:Silver Difficulty")) {
            amount = 2;
        } else {
            amount = 1;
        }
    }
    
    @Override
    public void onInitialApplication() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    
    @Override
    public void onVictory() {
        --AbstractDungeon.player.energy.energyMaster;
    }
    
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (isCustomModActive("challengethespire:Bronze Difficulty")) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), amount));
        } else if (isCustomModActive("challengethespire:Silver Difficulty")) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), amount));
        } else if (isCustomModActive("challengethespire:Gold Difficulty")) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), amount, true, true));
        } else if (isCustomModActive("challengethespire:Platinum Difficulty")) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new VoidCard(), amount));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), amount, true, true));
        }
    }
    
    @Override
    public void updateDescription() {
        if (isCustomModActive("challengethespire:Bronze Difficulty")) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[4];
        } else if (isCustomModActive("challengethespire:Silver Difficulty")) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3] + DESCRIPTIONS[4];
        } else if (isCustomModActive("challengethespire:Gold Difficulty")) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3] + DESCRIPTIONS[5];
        } else if (isCustomModActive("challengethespire:Platinum Difficulty")) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3] + DESCRIPTIONS[6];
        }
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


