package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import thiefmod.powers.ShadowFormPower;

import java.util.Iterator;

public class ShadowFormPlayAction extends AbstractGameAction {

    public ShadowFormPower power;

    public ShadowFormPlayAction(ShadowFormPower power) {
        this.power = power;
    }

    public void update() {

        power.playCardEffect(1);

        this.isDone = true;
    }
}
