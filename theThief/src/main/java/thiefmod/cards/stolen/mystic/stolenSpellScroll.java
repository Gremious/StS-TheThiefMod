package thiefmod.cards.stolen.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;

public class stolenSpellScroll extends AbstractBackstabCard {

    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("stolenSpellScroll");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private ArrayList<AbstractCard> artesGroup = new ArrayList<>();

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    private static final int MAGIC = 1;
    // /STAT DECLARATION/


    public stolenSpellScroll() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(true, MysticMod.returnTrulyRandomSpell()));
        } else {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(false, MysticMod.returnTrulyRandomSpell()));

        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeName();
            initializeDescription();
        }
    }
}