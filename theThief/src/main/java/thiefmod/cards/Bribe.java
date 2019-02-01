package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.common.GainGoldAction;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public class Bribe extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Bribe");
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
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;

    private static final int MAGIC = 20;
    private static final int UPGRADED_PLUS_MAGIC = -5;


// /STAT DECLARATION/

    public Bribe() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);


        ExhaustiveVariable.setBaseValue(this, 3);

        this.magicNumber = this.baseMagicNumber = MAGIC;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new GainGoldAction(p, p, this.magicNumber));

        AbstractDungeon.actionManager.addToBottom(
                new StunMonsterAction(m, p));
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
            this.upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}