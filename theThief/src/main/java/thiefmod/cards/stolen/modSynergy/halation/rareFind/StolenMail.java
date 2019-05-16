package thiefmod.cards.stolen.modSynergy.halation.rareFind;

import HalationCode.cards.LetterOfAdmiration;
import HalationCode.cards.LetterOfLove;
import HalationCode.cards.LetterOfRespect;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.WeakParticleEffect;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.util.MakeSuperCopyAction;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

@CardNoSeen
public class StolenMail extends AbstractStolenCard {
    // TEXT DECLARATION
    
    public static final String ID = ThiefMod.makeID("StolenMail");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    
    private static ArrayList<AbstractCard> letterCards = new ArrayList<>();
    // /STAT DECLARATION/
    
    public StolenMail() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.RARE, TheThiefEnum.THE_THIEF);
        if (letterCards.size() == 0) {
            letterCards.add(new  LetterOfAdmiration());
            letterCards.add(new LetterOfLove());
            letterCards.add(new LetterOfRespect());
        }
        
        tags.add(ThiefCardTags.RARE_FIND);
        this.exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.PINK));
        AbstractDungeon.effectList.add(new WeakParticleEffect(this.current_x, this.current_y, 1.0f, 1.0f));
        for (AbstractCard c : letterCards) {
            act(new MakeSuperCopyAction(c, p.hand));
            act(new MakeSuperCopyAction(c, p.drawPile));
            act(new MakeSuperCopyAction(c, p.discardPile));
        }
    }
    
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(EXTENDED_DESCRIPTION[0], EXTENDED_DESCRIPTION[1]));
        return tips;
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}