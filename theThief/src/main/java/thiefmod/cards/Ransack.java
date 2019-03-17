package thiefmod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.common.StealCardAction;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;

public class Ransack extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Ransack");
    public static final String IMG = "theThiefAssets/images/cards/beta/Ransack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");



    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int UPGRADED_PLUS_MAGIC = 1;



// /STAT DECLARATION/

    public Ransack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        action(new StealCardAction(
                1, magicNumber, false, AbstractDungeon.player.hand, false));

    }

    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber == 1) {
            rawDescription = EXTENDED_DESCRIPTION[1];
        } else {
            rawDescription = DESCRIPTION;
        }

        initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            initializeDescription();
        }
    }
}