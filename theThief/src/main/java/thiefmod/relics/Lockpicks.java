package thiefmod.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;

public class Lockpicks extends CustomRelic implements ClickableRelic, ModalChoice.Callback  {

    public static final String ID = thiefmod.ThiefMod.makeID("Lockpicks");
    public static final String IMG = "thiefmodAssets/images/relics/Lockpicks.png";
    public static final String OUTLINE = "thiefmodAssets/images/relics/outline/Lockpicks.png";

    private boolean usedThisCombat = false;
    private ModalChoice modal;

    public Lockpicks() {
        super(ID, ImageMaster.loadImage(IMG), new Texture(OUTLINE), RelicTier.BOSS, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));

        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.GREEN) // Sets color of any following archetypes to red
                .addOption("Fetch a card from your draw pile.", CardTarget.NONE)
                .setColor(CardColor.COLORLESS) // Sets color of any following archetypes to green
                .addOption("Fetch a card from your discard pile.", CardTarget.NONE)
                .setColor(CardColor.CURSE) // Sets color of any following archetypes to colorless
                .addOption("Fetch a card from your exhaust pile.", CardTarget.NONE)
                .create();
    }


    @Override
    public void onRightClick() {
        if (!isObtained || usedThisCombat) {
            return;
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisCombat = true;
            flash();
            stopPulse();

            modal.open();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        switch (i) {
            case 0:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, 1));
                break;
            case 1:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.discardPile, 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, 1));
                break;
        }
    }


    @Override
    public void atPreBattle() {
        usedThisCombat = false;
        beginLongPulse();
    }

    @Override
    public void onVictory() {
        stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
