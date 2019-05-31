package thiefmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.FastDarkSmoke;
import thiefmod.actions.common.ShadowstepAction;

public class IAmEverywhereAction extends AbstractGameAction {
    
    private boolean freeToPlayOnce = false;
    private int amount;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private boolean forEachMonster = false;
    
    public IAmEverywhereAction(AbstractPlayer p, int amount, boolean freeToPlayOnce, int energyOnUse, boolean forEachMonster) {
        this.p = p;
        this.amount = amount;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.forEachMonster = forEachMonster;
    }
    
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        
        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                
                if (forEachMonster = true) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        
                        //  new DarkSmokePuffEffect(p.drawX, p.drawY);
                        new FastDarkSmoke(p.drawX, p.drawY);
                        
                        AbstractDungeon.actionManager.addToBottom(new ShadowstepAction(p, p, amount));
                    }
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ShadowstepAction(p, p, amount));
                }
            }
            
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        
        isDone = true;
    }
}
