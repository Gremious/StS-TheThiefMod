package thiefmod.cards.shadowstep;

import basemod.helpers.TooltipInfo;
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
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

public class IAmEverywhere extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("IAmEverywhere");
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
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
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        tags.add(ThiefCardTags.SHADOWSTEP);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }

        action(new IAmEverywhereAction(p, magicNumber, freeToPlayOnce, energyOnUse, upgraded));


    }

    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}