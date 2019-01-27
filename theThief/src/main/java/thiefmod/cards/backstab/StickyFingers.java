package thiefmod.cards.backstab;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.StealCardAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.patches.Unique.ThiefCardTags;
import thiefmod.powers.Common.BackstabPower;

public class StickyFingers extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StickyFingers");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_SKILL);
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
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int STEAL = 2;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final String ADD_LOCATION = "Hand"; // If stolen card.
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public StickyFingers() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.backstabNumber = this.baseBackstabNumber= STEAL;

        this.tags.add(ThiefCardTags.BACKSTAB);
        this.tags.add(ThiefCardTags.STEALING);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1) {
            AbstractDungeon.actionManager.addToBottom(new StealCardAction(
                    backstabNumber, 1, ADD_RANDOM, ADD_LOCATION, ADD_UPGRADED));

        } else {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
        } else {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
        }

        this.initializeDescription();
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