package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.Iterator;

public class DartsAction extends AbstractGameAction {
    private DamageInfo info;

    public DartsAction(DamageInfo info) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.info = info;
        this.actionType = ActionType.DAMAGE;

    }

    @Override
    public void update() {
        Iterator skillCheckDamage = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while (skillCheckDamage.hasNext()) {
            AbstractCard c = (AbstractCard) skillCheckDamage.next();
            if (c.type == AbstractCard.CardType.SKILL) {

                AbstractMonster randomMonster =
                        AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);

                AbstractDungeon.actionManager.addToTop(new DamageAction(randomMonster, this.info, true));

                if (randomMonster != null && randomMonster.hb != null) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new ThrowDaggerEffect(randomMonster.hb.cX, randomMonster.hb.cY)));


                }
            }
        }

        this.isDone = true;
    }
}
