package thiefmod.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.cards.backstab.AttackOfOpportunity;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.util.TextureLoader;

public class SteakOut extends AbstractBackstabCard implements StartupCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("SteakOut");
    public static final String IMG = "theThiefAssets/images/cards/SteakOut.png";
    private static final Texture BETA_IMG = TextureLoader.getJokeTexture(getCardImageBeta(SteakOut.class.getSimpleName()), IMG);
    
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;
    // private static final int BACKSTAB = 1; Voids
    // /STAT DECLARATION/
    
    public SteakOut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        AutoplayField.autoplay.set(this, true);
        magicNumber = baseMagicNumber = MAGIC;
        jokePortrait = new TextureAtlas.AtlasRegion(BETA_IMG, 0, 0, 500, 380);
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        act(new GainEnergyAction(magicNumber));
    }
    
    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        //? act(
        //?          new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));
        act(new MakeTempCardInDiscardAndDeckAction(new VoidCard()));
        return true;
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