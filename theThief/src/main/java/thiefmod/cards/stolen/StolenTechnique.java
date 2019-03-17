package thiefmod.cards.stolen;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.CardNoSeen;
import thiefmod.actions.Util.MakeSuperCopyAction;
import thiefmod.actions.Util.getRandomCardFromAnyColor;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;

@CardNoSeen
public class StolenTechnique extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = thiefmod.ThiefMod.makeID("StolenTechnique");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    
    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    // /STAT DECLARATION/
    
    public StolenTechnique() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        getRandomCardFromAnyColor randomCards = new getRandomCardFromAnyColor(1);
        ArrayList<AbstractCard> cards = new ArrayList<>(getRandomCardFromAnyColor.getListOfRandomCards());
        AbstractCard card = cards.get(0);
        card.freeToPlayOnce = true;
        act(new MakeSuperCopyAction(card, p.hand));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeBaseCost(UPGRADE_COST);
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}