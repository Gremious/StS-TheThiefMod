package thiefmod.cards.stolen.modSynergy.mystic;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mysticmod.MysticMod;
import mysticmod.cards.Fireball;
import mysticmod.patches.MysticEnum;
import mysticmod.patches.MysticTags;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.common.playCardWithRandomTargestAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.cards.abstracts.AbstractStolenMysticCard;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class stolenSpellScroll extends AbstractStolenMysticCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("stolenSpellScroll");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 0;
    private static final int MAGIC = 1;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? Fireball.ALTERNATE_IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenSpellScroll() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
        if (ThiefMod.hasMysticMod) tags.add(MysticTags.IS_SPELL);
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = MysticMod.returnTrulyRandomSpell();
        if (upgraded) card.upgrade();
        AbstractDungeon.actionManager.addToTop(new playCardWithRandomTargestAction(true, MysticMod.returnTrulyRandomSpell()));
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