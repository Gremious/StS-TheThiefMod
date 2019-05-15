package thiefmod.cards.stolen.rareFind;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.StolenArsenalAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class StolenArsenal extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenArsenal");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    // /STAT DECLARATION/
    
    public StolenArsenal() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.RARE, AbstractPlayer.PlayerClass.THE_SILENT);
        
        tags.add(ThiefCardTags.RARE_FIND);
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int maximumHand = BaseMod.MAX_HAND_SIZE;
        int currentHand = p.hand.group.size();
        AbstractDungeon.actionManager.addToTop(new StolenArsenalAction(p));
        do {
            act(new DrawCardAction(p, 1));
            if (p.drawPile.isEmpty() && p.discardPile.isEmpty()) {
                break;
            }
            currentHand++;
        } while (currentHand != maximumHand);
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}