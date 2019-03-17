package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
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

public class LieInWait extends AbstractBackstabCard implements StartupCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("LieInWait");
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

    private static final int COST = 1;

    private static final int BLOCK = 11;

    private static final int MAGIC = 1;

    private static final int BACKSTAB = 2;
    private static final int UPGRADED_BACKSTAB = 3;


// /STAT DECLARATION/

    public LieInWait() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC;
        baseBlock = BLOCK;
        backstabNumber = baseBackstabNumber = BACKSTAB;

        ExhaustiveVariable.setBaseValue(this, backstabNumber);
    }

    // Actions the card should do.

    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        action(
                new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));
        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        action(new GainBlockAction(
                p, p, block));

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
            upgradeBackstabNumber(UPGRADED_BACKSTAB);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}