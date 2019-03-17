package thiefmod.cards.backstab;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.util.TextureLoader;

public class AttackOfOpportunity extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("AttackOfOpportunity");
    public static final String IMG = "theThiefAssets/images/cards/beta/AttackOfOpportunity.png";
    private static final Texture BETA_IMG = TextureLoader.getJokeTexture(getCardImageBeta(AttackOfOpportunity.class.getSimpleName()), IMG);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 1;
    
    private static final int DAMAGE = 16;
    
    private static final int MAGIC = 1; // Card draw
    private static final int UPGRADED_PLUS_MAGIC = 1;
    
    private static final int BACKSTAB = 2; // Voids
    private static final int UPGRADED_PLUS_BACKSTAB = -1;
    // /STAT DECLARATION/
    
    public AttackOfOpportunity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        jokePortrait = new TextureAtlas.AtlasRegion(BETA_IMG, 0, 0, 500, 380);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;
        tags.add(ThiefCardTags.BACKSTAB);
        initializeDescription();
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Deal 16 Damage
        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        // Add voids to your draw pile
        if (backstabNumber != 0) {
            act(new MakeTempCardInDrawPileAction(new VoidCard(), backstabNumber, true, true));
        }
        // Backstab - Draw 1 card.
        if (canBackstab()) {
            act(new DrawCardAction(p, magicNumber));
        }
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (backstabNumber == 1) {
            if (magicNumber == 1) {
                if (canBackstab()) {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[4];
                }
            } else {
                if (canBackstab()) {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[5];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[6];
                }
            }
        } else {
            if (magicNumber == 1) {
                if (canBackstab()) {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[4];
                }
            } else {
                if (canBackstab()) {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[5];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[6];
                }
            }
        }
        initializeDescription();
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}