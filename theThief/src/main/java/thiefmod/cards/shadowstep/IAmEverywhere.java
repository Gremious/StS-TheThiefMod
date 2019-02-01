package thiefmod.cards.shadowstep;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.IAmEverywhereAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.patches.Character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

public class IAmEverywhere extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("IAmEverywhere");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:FlavorText");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;

// /STAT DECLARATION/

    public IAmEverywhere() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.tags.add(ThiefCardTags.SHADOWSTEP);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new IAmEverywhereAction(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse, this.upgraded));


    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], EXTENDED_DESCRIPTION[0]));
        // tips.addAll(modal.generateTooltips());
        return tips;
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}