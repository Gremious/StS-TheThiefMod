package thiefmod.cards.backstab;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.BackstabPower;

import java.util.ArrayList;
import java.util.List;

public class Backstep extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Backstep");
    public static final String IMG = "thiefmodAssets/images/cards/beta/Backstep.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

// /TEXT DECLARATION/


    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    private static final int MAGIC = 1;

// /STAT DECLARATION/

    public Backstep() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        ExhaustiveVariable.setBaseValue(this, 2);

        magicNumber = baseMagicNumber = MAGIC;

        tags.add(ThiefCardTags.BACKSTAB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (count <= 1) {

            AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.discardPile, magicNumber));

        } else {

            AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.discardPile, magicNumber,
                    fetchedCards -> {
                        for (AbstractCard card : fetchedCards) {
                            card.modifyCostForTurn(-1);
                        }
                    }));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber >= 2) {
            rawDescription = EXTENDED_DESCRIPTION[4];
        } else {
            rawDescription = DESCRIPTION;
        }

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
        } else {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
        }

        initializeDescription();
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
            initializeDescription();
        }
    }
}