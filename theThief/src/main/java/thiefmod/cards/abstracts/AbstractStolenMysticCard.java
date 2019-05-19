package thiefmod.cards.abstracts;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import mysticmod.MysticMod;
import mysticmod.patches.MysticTags;
import thiefmod.CardIgnore;

import java.util.ArrayList;
import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static thiefmod.ThiefMod.getModID;

@CardIgnore
public abstract class AbstractStolenMysticCard extends AbstractStolenCard {
    
    public static CardStrings tooltip = CardCrawlGame.languagePack.getCardStrings("mysticmod:AbstractMysticCard");
    public static String[] tooltips = tooltip.EXTENDED_DESCRIPTION;
    
    public AbstractStolenMysticCard(String id, String img, int cost, CardType type, CardTarget target,
                                    CardRarity subRarity, AbstractPlayer.PlayerClass character) {
        super(id, img, cost, type, target, subRarity, character);
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
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