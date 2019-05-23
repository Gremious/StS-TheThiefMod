package thiefmod.cards.abstracts;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import mysticmod.MysticMod;
import mysticmod.patches.MysticTags;
import thiefmod.CardIgnore;

import java.util.ArrayList;
import java.util.List;

import static thiefmod.patches.character.ThiefCardTags.MYSTIC_ARTE_TOOLTIP;
import static thiefmod.patches.character.ThiefCardTags.MYSTIC_SPELL_TOOLTIP;

@CardIgnore
public abstract class AbstractStolenMysticCard extends AbstractStolenCard {
    
    public static CardStrings tooltip = CardCrawlGame.languagePack.getCardStrings("mysticmod:AbstractMysticCard");
    public static UIStrings keyword = CardCrawlGame.languagePack.getUIString("theThief:MysticMod");
    
    public static String[] tooltips = tooltip.EXTENDED_DESCRIPTION;
    public static String[] keywords = keyword.TEXT;
    
    public AbstractStolenMysticCard(String id, String img, int cost, CardType type, CardTarget target,
                                    CardRarity subRarity, AbstractPlayer.PlayerClass character) {
        super(id, img, cost, type, target, subRarity, character);
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        if (this.hasTag(MYSTIC_SPELL_TOOLTIP)) {
            tips.add(new TooltipInfo(keywords[0], keywords[2]));
        }
        
        if (this.hasTag(MYSTIC_ARTE_TOOLTIP)) {
            tips.add(new TooltipInfo(keywords[1], keywords[3]));
        }
        
        if (type == AbstractCard.CardType.SKILL) {
            if (MysticMod.isThisASpell(this)) {
                tips.add(new TooltipInfo(tooltips[0], tooltips[2]));
            }
            if (MysticMod.isThisAnArte(this)) {
                tips.add(new TooltipInfo(tooltips[1], tooltips[3]));
            }
        } else if (type == AbstractCard.CardType.ATTACK) {
            if (MysticMod.isThisASpell(this)) {
                tips.add(new TooltipInfo(tooltips[0], tooltips[4]));
            }
            if (MysticMod.isThisAnArte(this)) {
                tips.add(new TooltipInfo(tooltips[1], tooltips[5]));
            }
        }
        
        if (hasTag(MysticTags.IS_POWERFUL)) {
            tips.add(new TooltipInfo(tooltips[6], tooltips[8]));
        }
        if (hasTag(MysticTags.IS_POISED)) {
            tips.add(new TooltipInfo(tooltips[7], tooltips[9]));
        }
        return tips;
    }
}