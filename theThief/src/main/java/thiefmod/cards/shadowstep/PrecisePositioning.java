package thiefmod.cards.shadowstep;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PhantasmalPower;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.ShadowstepPower;

import java.util.ArrayList;
import java.util.List;

public class PrecisePositioning extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("PrecisePositioning");
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
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 3;

    private static final int MAGIC = 1;

// /STAT DECLARATION/

    public PrecisePositioning() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        RefundVariable.setBaseValue(this, 2);

        isEthereal = true;

        magicNumber = baseMagicNumber = MAGIC;

        tags.add(ThiefCardTags.SHADOWSTEP);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ShadowstepPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PhantasmalPower(p, magicNumber), magicNumber));


    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber >= 2) {
            if (upgraded) {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[5];
            } else {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
            }
        } else {
            if (upgraded) {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[4];
            } else {
                rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
            }
        }

        initializeDescription();
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
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}