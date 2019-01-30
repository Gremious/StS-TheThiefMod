package thiefmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Neutralize;
import com.megacrit.cardcrawl.cards.red.DoubleTap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.actions.Util.DiscoverRandomFromColorAction;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;

public class AAAtestCard extends AbstractBackstabCard {
private ArrayList<AbstractCard> cardsToDiscoverFrom = new ArrayList<>();

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("testCard");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;

    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final int BACKSTAB = 1;
    private static final int UPGRADED_PLUS_BACKSTAB = 1;

    private static final String ADD_LOCATION = "Hand"; // If stolen card.
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public AAAtestCard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        cardsToDiscoverFrom.add(new DoubleTap());
        cardsToDiscoverFrom.add(new AAAtestCard());
        cardsToDiscoverFrom.add(new Neutralize());
        //
        //  Second: see if you can pre-fill derp, and have an if derp != null, just fetch the cards from it rather than filling it
        AbstractDungeon.actionManager.addToBottom(new DiscoverRandomFromArrayAction(cardsToDiscoverFrom));
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
            initializeDescription();
        }
    }
}