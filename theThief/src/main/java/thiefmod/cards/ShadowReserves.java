package thiefmod.cards;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.patches.character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public class ShadowReserves extends AbstractBackstabCard implements ModalChoice.Callback {
// implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("ShadowReserves");
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
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    private static final int MAGIC = 1;

    private ModalChoice modal;
// /STAT DECLARATION/

    public ShadowReserves() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        ExhaustiveVariable.setBaseValue(this, 2);

        magicNumber = baseMagicNumber = MAGIC;


        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.GREEN) // Sets color of any following cards to red
                .addOption("Fetch a card from your draw pile.", CardTarget.NONE)
                .setColor(CardColor.COLORLESS) // Sets color of any following cards to green
                .addOption("Fetch a card from your discard pile.", CardTarget.NONE)
                .setColor(CardColor.CURSE) // Sets color of any following cards to colorless
                .addOption("Fetch a card from your exhaust pile.", CardTarget.NONE)
                .create();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, magicNumber));
        } else {
            modal.open();
        }
    }

    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i) {
        switch (i) {
            case 0:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.discardPile, magicNumber));
                break;
            case 2:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, magicNumber));
                break;
            default:
                return;
        }
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