package thiefmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;

public class HideInTheShadows extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("HideInTheShadows");
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
    private ArrayList<AbstractCard> thisTurnCardsArray = AbstractDungeon.actionManager.cardsPlayedThisTurn;


    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;
// /STAT DECLARATION/

    public HideInTheShadows() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;
        this.isEthereal = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard AttackCheckCard : thisTurnCardsArray) {
            if (AttackCheckCard.type == CardType.ATTACK)
                exhaustOnUseOnce = true;
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(
                p, p, block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.rawDescription = DESCRIPTION;

        for (AbstractCard AttackCheckCard : thisTurnCardsArray) {
            if (AttackCheckCard.type == CardType.ATTACK)
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
        }

        this.initializeDescription();
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.initializeDescription();
        }
    }
}