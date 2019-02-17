package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import thiefmod.powers.Common.RefundCardCostPower;

import java.util.ArrayList;
import java.util.List;

public class PaydayLoan extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("PaydayLoan");
    public static final String IMG = "thiefmodAssets/images/cards/beta/Attack.png";
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
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 0;

    private static final int MAGIC = 1; // Refund Card Power

    private static final int BACKSTAB = 1; // Voids

// /STAT DECLARATION/

    public PaydayLoan() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        ExhaustiveVariable.setBaseValue(this, 2);

        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
                p, p, new RefundCardCostPower(
                p, p, magicNumber), 1));


        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(
                        new VoidCard(), backstabNumber, true, true, false));

    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], EXTENDED_DESCRIPTION[0]));
        return tips;
    }


    //Upgraded stats.
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