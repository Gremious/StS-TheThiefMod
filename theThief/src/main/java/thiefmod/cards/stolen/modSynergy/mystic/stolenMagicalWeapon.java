package thiefmod.cards.stolen.modSynergy.mystic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.cards.Spellstrike;
import mysticmod.patches.MysticEnum;
import mysticmod.patches.MysticTags;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.cards.abstracts.AbstractStolenMysticCard;

@CardNoSeen
public class stolenMagicalWeapon extends AbstractStolenMysticCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("stolenMagicalWeapon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardTarget TARGET = CardTarget.ALL;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? Spellstrike.IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenMagicalWeapon() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        if (ThiefMod.hasMysticMod) tags.add(MysticTags.IS_ARTE);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = MysticMod.returnTrulyRandomArte();
        if (upgraded) card.upgrade();
        AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(true, card));
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