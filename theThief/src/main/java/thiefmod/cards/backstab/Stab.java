package thiefmod.cards.backstab;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.BackstabPower;

public class Stab extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Stab");
    public static final String IMG = "thiefmodAssets/images/cards/Stab.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/


// STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    private static final int DAMAGE = 5;
    private static final int UPGRADED_DAMAGE = 2;

    private static final int BACKSTAB = 2;

// /STAT DECLARATION/

    public Stab() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        baseBackstabNumber = backstabNumber = BACKSTAB;

        tags.add(ThiefCardTags.BACKSTAB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1 || p.hasPower(BackstabPower.POWER_ID)) {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, damage * backstabNumber, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, damage, damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player.cardsPlayedThisTurn == 0 || AbstractDungeon.player.hasPower(BackstabPower.POWER_ID)) {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
        } else {
            rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
        }

        initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADED_DAMAGE);
            initializeDescription();
        }
    }
}