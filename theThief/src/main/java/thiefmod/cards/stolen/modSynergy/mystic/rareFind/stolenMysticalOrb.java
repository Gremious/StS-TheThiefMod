package thiefmod.cards.stolen.modSynergy.mystic.rareFind;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import mysticmod.cards.Daze;
import mysticmod.patches.MysticEnum;
import mysticmod.patches.MysticTags;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.deprecatedMysticalOrbAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.cards.abstracts.AbstractStolenMysticCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class stolenMysticalOrb extends AbstractStolenMysticCard {
    
    // TEXT DECLARATION
    private Color mysticPurple = new Color(152.0F, 34.0F, 171.0F, 1.0F);
    public static final String ID = ThiefMod.makeID("stolenMysticalOrb");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;
    // /STAT DECLARATION/
    
    public static final String IMG = (ThiefMod.hasMysticMod ? Daze.ALTERNATE_IMG_PATH : loadLockedCardImage(TYPE));
    
    public stolenMysticalOrb() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.RARE, MysticEnum.MYSTIC_CLASS);
        magicNumber = baseMagicNumber = MAGIC;
        
        tags.add(ThiefCardTags.RARE_FIND);
        
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(mysticPurple));
        for (int i = 0; i < magicNumber; i++) {
            AbstractDungeon.actionManager.addToTop(new deprecatedMysticalOrbAction());
        }
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        super.getCustomTooltips();
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
    }
    
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            upgradeName();
            initializeDescription();
        }
    }
}