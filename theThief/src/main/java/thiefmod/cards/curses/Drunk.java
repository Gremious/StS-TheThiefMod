package thiefmod.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;

@CardNoSeen
public class Drunk extends AbstractBackstabCard {

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Drunk");
    public static final String IMG = "theThiefAssets/images/cards/CallOfTheVoid.png";
    public static final CardColor COLOR = CardColor.CURSE;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);



// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;

    private static final int COST = 0;


// /STAT DECLARATION/

    public Drunk() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AutoplayField.autoplay.set(this, true);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(
                new MakeTempCardInDiscardAndDeckAction(new Dazed()));

        act(
                new MakeTempCardInDiscardAndDeckAction(new Dazed()));
    }
    @Override
    public String flavortext() {
        return null;
    }
    @Override
    public void upgrade() {
    }
}