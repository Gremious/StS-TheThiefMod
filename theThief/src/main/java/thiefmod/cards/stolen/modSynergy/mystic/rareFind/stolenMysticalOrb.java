package thiefmod.cards.stolen.modSynergy.mystic.rareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.stolenMysticalOrbAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class stolenMysticalOrb extends AbstractStolenCard {
    
    // TEXT DECLARATION
    private Color mysticPurple = new Color(152.0F, 34.0F, 171.0F, 1.0F);
    public static final String ID = ThiefMod.makeID("stolenMysticalOrb");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private ArrayList<AbstractCard> artesGroup = new ArrayList<>();
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 4;
    // /STAT DECLARATION/
    
    public stolenMysticalOrb() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        setBannerTexture("theThiefAssets/images/512/special/rare_skill_banner.png", "theThiefAssets/images/1024/special/rare_skill_banner.png");
        magicNumber = baseMagicNumber = MAGIC;
        tags.add(ThiefCardTags.STOLEN);
        tags.add(ThiefCardTags.RARE_FIND);
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(mysticPurple));
        AbstractDungeon.actionManager.addToTop(new stolenMysticalOrbAction(true, magicNumber));
    }
    
    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.effectList.add(new CardFlashVfx(this, mysticPurple));
    }
    
    @Override
    public void triggerWhenCopied() {
        AbstractDungeon.effectList.add(new CardFlashVfx(this, mysticPurple));
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
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