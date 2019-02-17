package thiefmod.cards.backstab;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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

public class Prep extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Prep");
    public static final String IMG = "thiefmodAssets/images/cards/generic_beta_cards/beta_attack_green_3.png";
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
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    private static final int BLOCK = 8;

    private static final int MAGIC = 1;

// /STAT DECLARATION/


    public Prep() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;

        tags.add(ThiefCardTags.BACKSTAB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                p, p, block));

        if (canBackstab()) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (canBackstab()) {
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