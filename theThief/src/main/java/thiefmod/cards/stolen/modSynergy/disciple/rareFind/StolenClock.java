package thiefmod.cards.stolen.modSynergy.disciple.rareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.InspiringSong;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import mysticmod.cards.Snowball;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.powers.Unique.StolenClockPower;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class StolenClock extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenClock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    public StolenClock() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.RARE, chronomuncher.patches.Enum.CHRONO_CLASS);
        exhaust = true;
        
        if (ThiefMod.hasDisciple) {
            portrait = new chronomuncher.cards.SecondHand().portrait;
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.BROWN));
        AbstractDungeon.actionManager.addToTop(new SFXAction("POWER_TIME_WARP"));
        act(new ApplyPowerAction(p, p, new StolenClockPower(p, 1), 0));
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasDisciple) {
            return AbstractBardCard.getPortraitImage(new chronomuncher.cards.SecondHand());
        } else {
            return super.getPortraitImage();
        }
    }
    
/*    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
    }
    */
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}