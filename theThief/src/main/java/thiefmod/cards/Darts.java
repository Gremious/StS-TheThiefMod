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
import thiefmod.patches.Character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Darts extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Darts");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("FlavorText");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    private static final int DAMAGE = 1;

// /STAT DECLARATION/


    public Darts() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new DartsAction(new DamageInfo(p, this.damage, this.damageTypeForTurn)));

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                    new DartsAction(new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }

        this.rawDescription = DESCRIPTION;
        this.initializeDescription();


    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        Iterator skillCheckDescription = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();

        while (skillCheckDescription.hasNext()) {
            AbstractCard c = (AbstractCard) skillCheckDescription.next();
            if (c.type == CardType.SKILL) {
                ++count;
            }
        }

        this.rawDescription = DESCRIPTION;
        ;
        if (count == 1) {
            this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[1] + count + EXTENDED_DESCRIPTION[2];
        } else if (count > 1) {
            this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[1] + count + EXTENDED_DESCRIPTION[3];
        } else {
            this.rawDescription = this.rawDescription + EXTENDED_DESCRIPTION[4];
        }

        this.initializeDescription();
    }


    @Override
    public List<TooltipInfo> getCustomTooltips() {
        List<TooltipInfo> tips = new ArrayList<>();
        tips.add(new TooltipInfo(FLAVOR_STRINGS[0], EXTENDED_DESCRIPTION[0]));
        return tips;
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Darts();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}