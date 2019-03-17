package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.actions.unique.DartsAction;
import thiefmod.patches.character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public class Darts extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Darts");
    public static final String IMG = "theThiefAssets/images/cards/beta/Darts.png";
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
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    private static final int DAMAGE = 1;
    private static final int UPGRADED_DAMAGE = 1;

// /STAT DECLARATION/


    public Darts() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new DartsAction(new DamageInfo(p, damage, damageTypeForTurn)));

        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new DartsAction(new DamageInfo(p, damage, damageTypeForTurn)));
        }

        rawDescription = DESCRIPTION;
        initializeDescription();


    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.SKILL) {
                ++count;
            }
        }

        rawDescription = DESCRIPTION;

        if (count == 1) {
            rawDescription = rawDescription + EXTENDED_DESCRIPTION[1] + count + EXTENDED_DESCRIPTION[2];
        } else if (count > 1) {
            rawDescription = rawDescription + EXTENDED_DESCRIPTION[1] + count + EXTENDED_DESCRIPTION[3];
        } else {
            rawDescription = rawDescription + EXTENDED_DESCRIPTION[4];
        }

        initializeDescription();
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], EXTENDED_DESCRIPTION[0]));
        return tips;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeDamage(UPGRADED_DAMAGE);
            upgradeName();
            initializeDescription();
        }
    }
}