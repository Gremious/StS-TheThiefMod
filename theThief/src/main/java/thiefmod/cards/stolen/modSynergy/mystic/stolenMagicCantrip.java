package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import thiefmod.ThiefMod;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;

import static mysticmod.MysticMod.cantripsGroup;

import thiefmod.CardNoSeen;

@CardNoSeen
public class stolenMagicCantrip extends AbstractStolenCard {

    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("stolenMagicCantrip");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";


    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private ArrayList<AbstractCard> artesGroup = new ArrayList<>();

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;

    private static final int MAGIC = 1;
    // /STAT DECLARATION/


    public stolenMagicCantrip() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard trinketCard = cantripsGroup.get(AbstractDungeon.cardRandomRng.random(cantripsGroup.size() - 1));
        if (upgraded) {
            trinketCard.upgrade();
            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(trinketCard));
        } else {

            AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(trinketCard));
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