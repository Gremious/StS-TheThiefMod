package thiefmod.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.util.TextureLoader;

public class Ambush extends AbstractBackstabCard {
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("Ambush");
    public static final String IMG = "theThiefAssets/images/cards/beta/Ambush.png";
    private static final Texture BETA_IMG = TextureLoader.getJokeTexture(getCardImageBeta(Ambush.class.getSimpleName()), IMG);
    
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    
    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    // /STAT DECLARATION/
    
    public Ambush() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        jokePortrait = new TextureAtlas.AtlasRegion(BETA_IMG, 0, 0, 500, 380);
        damage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        exhaust = true;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));
        act(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
    
    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        tmp = 0.0f;
        if (mo == null || mo.maxHealth == 0) {
            return tmp;
        }
        float damag;
        if (upgraded) {
            damag = (float) mo.currentHealth / 2;
        } else {
            damag = (float) mo.currentHealth / 3.3f;
        }
        return damag;
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
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}