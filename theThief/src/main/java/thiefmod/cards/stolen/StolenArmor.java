package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.StolenArmorAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;


@CardNoSeen
public class StolenArmor extends AbstractStolenCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenArmor");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";


    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final int BACKSTAB = 1;

    // /STAT DECLARATION/


    public StolenArmor() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);


        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;


        tags.add(ThiefCardTags.STOLEN);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        action(new ArmamentsAction(false));

        if (AbstractDungeon.player.drawPile.isEmpty()) {
            action(new EmptyDeckShuffleAction());
        }

        action(new StolenArmorAction(block));
        action(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();

            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBlock(UPGRADE_PLUS_BLOCK);

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}