package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import mysticmod.cards.ComboCaster;
import mysticmod.patches.MysticEnum;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;

import java.util.ArrayList;

import static mysticmod.MysticMod.cantripsGroup;

@CardNoSeen
public class stolenMagicCantrip extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenMagicCantrip");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private ArrayList<AbstractCard> artesGroup = new ArrayList<>();
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 1;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? ComboCaster.IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenMagicCantrip() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
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