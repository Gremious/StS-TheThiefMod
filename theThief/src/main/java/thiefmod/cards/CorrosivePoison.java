package thiefmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.CorrosivePoisonAction;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.Iterator;

public class CorrosivePoison extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("CorrosivePoison");
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
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 4;

    private static final int MAGIC = 2;
    private static final int UPGRADED_PLUS_MAGIC = 2;


// /STAT DECLARATION/

    public CorrosivePoison() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC;

    }

    public void configureCostsOnNewCard() {
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            if (c.type == CardType.SKILL) {
                updateCost(-1);
            }
        }

    }

    @Override
    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            updateCost(-1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToBottom(new CorrosivePoisonAction(randomMonster, 2, 1, 2, magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
//          this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}