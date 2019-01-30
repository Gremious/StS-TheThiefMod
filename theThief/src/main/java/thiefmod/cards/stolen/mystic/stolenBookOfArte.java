package thiefmod.cards.stolen.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.actions.SpellDiscoveryAction;
import thiefmod.ThiefMod;
import thiefmod.Utils;
import thiefmod.actions.Util.DiscoverRandomFromArrayAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;

public class stolenBookOfArte extends AbstractBackstabCard {

    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("stolenBookOfArte");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;

    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 2;
    // /STAT DECLARATION/


    public stolenBookOfArte() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {

            ArrayList<AbstractCard> threeRandomArtes = new ArrayList<>();
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());
            threeRandomArtes.add(MysticMod.returnTrulyRandomArte());

            AbstractDungeon.actionManager.addToBottom(new DiscoverRandomFromArrayAction(threeRandomArtes));
            threeRandomArtes.clear();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (this.magicNumber >= 2) {
            this.rawDescription = UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION;
        }

        this.initializeDescription();
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