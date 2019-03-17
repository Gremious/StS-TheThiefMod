package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;

import thiefmod.CardNoSeen;

@CardNoSeen
public class stolenBookOfArte extends AbstractStolenCard {

    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("stolenBookOfArte");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";


    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 2;
    // /STAT DECLARATION/


    public stolenBookOfArte() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {

            ArrayList<AbstractCard> threeRandomArtes = new ArrayList<>();
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());

            action(new DiscoverRandomFromArrayAction(threeRandomArtes));
            threeRandomArtes.clear();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (magicNumber >= 2) {
            rawDescription = UPGRADE_DESCRIPTION;
        } else {
            rawDescription = DESCRIPTION;
        }

        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_MAGIC);
            initializeDescription();
        }
    }
}