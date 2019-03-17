package thiefmod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;

public class HideInTheShadows extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("HideInTheShadows");
    public static final String IMG = "theThiefAssets/images/cards/beta/HideInTheShadows.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // /STAT DECLARATION/
    
    public HideInTheShadows() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        isEthereal = true;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard AttackCheckCard : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (AttackCheckCard.type == CardType.ATTACK) {
                this.exhaustOnUseOnce = true;
                return;
            }
        }
        act(new GainBlockAction(p, p, block));
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        rawDescription = DESCRIPTION;
        for (AbstractCard AttackCheckCard : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (AttackCheckCard.type == CardType.ATTACK) rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}