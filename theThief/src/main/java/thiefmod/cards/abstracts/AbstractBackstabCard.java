package thiefmod.cards.abstracts;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardIgnore;
import thiefmod.powers.Common.BackstabPower;

import java.util.ArrayList;
import java.util.List;

@CardIgnore
public abstract class AbstractBackstabCard extends AbstractThiefCard {
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    
    public AbstractBackstabCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }
    
    public static boolean canBackstab() {
        return AbstractDungeon.player.cardsPlayedThisTurn < 1 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID);
    }
    
    public abstract String flavortext();
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
    super.calculateCardDamage(mo);
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (flavortext() != null) {
            tips.add(new TooltipInfo(FLAVOR_STRINGS[0], flavortext()));
        } else {
            tips.add(new TooltipInfo(FLAVOR_STRINGS[0], FLAVOR_STRINGS[0]));
        }
        return tips;
    }
}