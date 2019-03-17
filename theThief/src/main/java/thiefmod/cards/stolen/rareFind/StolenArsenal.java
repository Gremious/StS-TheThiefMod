package thiefmod.cards.stolen.rareFind;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.StolenArsenalAction;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.ThiefCardTags;

import java.util.ArrayList;
import java.util.List;

import thiefmod.CardNoSeen;

@CardNoSeen
public class StolenArsenal extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("StolenArsenal");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = "theThiefAssets/images/cards/beta/Attack.png";

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


    // /STAT DECLARATION/


    public StolenArsenal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

     /* Straight up just doesn't work. But maybe one day it will. And when that happens, I'll be waiting. And I will uncomment this code. And my rare cards will look cool.

        setBackgroundTexture("theThiefAssets/images/512/special/red_rare_skill_bg.png",
                "theThiefAssets/images/1024/special/red_rare_skill_bg.png");

        setOrbTexture("theThiefAssets/images/512/card_thief_gray_orb.png",
                "theThiefAssets/images/1024/card_thief_gray_orb.png");
    */
        setBannerTexture("theThiefAssets/images/512/special/rare_skill_banner.png",
                "theThiefAssets/images/1024/special/rare_skill_banner.png");

        tags.add(ThiefCardTags.STOLEN);
        tags.add(ThiefCardTags.RARE_FIND);
        exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int maximumHand = BaseMod.MAX_HAND_SIZE;
        int currentHand = p.hand.group.size();

        AbstractDungeon.actionManager.addToTop(new StolenArsenalAction(p));

        do {
            action(new DrawCardAction(p, 1));
            if (p.drawPile.isEmpty() && p.discardPile.isEmpty()) {
                break;
            }
            currentHand++;
        }
        while (currentHand != maximumHand);
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
        if (!upgraded) {
            upgradeName();

            upgradeBaseCost(UPGRADE_COST);

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}