package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.cards.Snowball;
import mysticmod.patches.MysticEnum;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.abstracts.AbstractStolenCard;

import java.util.ArrayList;

@CardNoSeen
public class stolenArteScroll extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenArteScroll");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = (ThiefMod.hasMysticMod ? Snowball.IMG_PATH : null);
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private ArrayList<AbstractCard> artesGroup = new ArrayList<>();
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ALL;
    // /STAT DECLARATION/
    
    public stolenArteScroll() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(false, MysticMod.returnTrulyRandomArte()));
        } else {
            AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(true, MysticMod.returnTrulyRandomArte()));
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