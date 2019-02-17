package thiefmod.cards;

import basemod.BaseMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.patches.character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public class HuntersInstinct extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("HuntersInstinct");
    public static final String IMG = "thiefmodAssets/images/cards/beta/Attack.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final int MAGIC = 3;

// /STAT DECLARATION/

    public HuntersInstinct() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        exhaust = true;

        magicNumber = baseMagicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int maximumHand = BaseMod.MAX_HAND_SIZE;
        int currentHand = p.hand.group.size();

        do {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
            if (!AbstractDungeon.player.drawPile.isEmpty() && !AbstractDungeon.player.discardPile.isEmpty()) {
                break;
            }
            currentHand++;
        }
        while (currentHand != maximumHand);

        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true, false));

    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], EXTENDED_DESCRIPTION[0]));
        // tips.addAll(modal.generateTooltips());
        return tips;
    }


    //Upgraded stats.
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