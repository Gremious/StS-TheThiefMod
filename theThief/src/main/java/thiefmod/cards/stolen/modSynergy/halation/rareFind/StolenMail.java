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
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import com.megacrit.cardcrawl.vfx.combat.WeakParticleEffect;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.actions.Util.MakeSuperCopyAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

import thiefmod.CardNoSeen;

@CardNoSeen
public class StolenMail extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenMail");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;


    private static ArrayList<AbstractCard> letterCards = new ArrayList<>();
    // /STAT DECLARATION/


    public StolenMail() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        if (letterCards.size() == 0) {
            letterCards.add(new LetterOfAdmiration());
            letterCards.add(new LetterOfLove());
            letterCards.add(new LetterOfRespect());
        }

        tags.add(ThiefCardTags.STOLEN);
        tags.add(ThiefCardTags.RARE_FIND);

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.effectList.add(new BorderFlashEffect(Color.PINK));
        AbstractDungeon.effectList.add(new WeakParticleEffect(this.current_x, this.current_y, 1.0f, 1.0f));

        for (AbstractCard c : letterCards) {
            AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(c, p.hand));
            AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(c, p.drawPile));
            AbstractDungeon.actionManager.addToBottom(new MakeSuperCopyAction(c, p.discardPile));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));
    }

    @Override
    public void triggerWhenCopied() {
        AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));
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