package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import thiefmod.ThiefMod;
import thiefmod.actions.common.StealCardAction;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.patches.Unique.ThiefCardTags;
import thiefmod.powers.Common.ShadowstepPower;

import java.util.ArrayList;
import java.util.List;

public class RiggedBet extends AbstractBackstabCard {
//implements StartupCard
//implements ModalChoice.Callback

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("RiggedBet"); // I really wish I could name this Overcalculated Gamble :C
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final String ADD_LOCATION = "Hand"; // If stolen card.
    private static final boolean ADD_RANDOM = true;
    private static final boolean ADD_UPGRADED = false;

// /STAT DECLARATION/

    public RiggedBet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = MAGIC;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(
                    new DiscardAction());

    }

    /*

    @Override // Startup: Add 1 void to your draw pile.
    public boolean atBattleStartPreDraw() {
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInDrawPileAction(new VoidCard(), this.magicNumber, true, true, false));
        return true;
    }

    */

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (this.magicNumber >= 2) {
            this.rawDescription = UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION;
        }

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            this.rawDescription += this.EXTENDED_DESCRIPTION[1];
        } else {
            this.rawDescription += this.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    /*
        @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        switch (i) {
            case 0:
                 AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.drawPile, this.magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.discardPile, this.magicNumber));
                break;
            case 2:
                AbstractDungeon.actionManager.addToTop(new FetchAction(AbstractDungeon.player.exhaustPile, this.magicNumber));
                break;
            default:
                return;
        }
    }
     */
    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo("Flavor Text", EXTENDED_DESCRIPTION[0]));
        // tips.addAll(modal.generateTooltips());
        return tips;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RiggedBet();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}