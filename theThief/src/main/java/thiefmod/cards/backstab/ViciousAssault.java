package thiefmod.cards.backstab;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

import java.util.ArrayList;
import java.util.List;

public class ViciousAssault extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("ViciousAssault");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_UNCOMMON_ATTACK);
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
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;

    private static final int DAMAGE = 4;

    private static final int MAGIC = 3;
    private static final int UPGRADED_PLUS_MAGIC = 1;

    private static final int BACKSTAB = 4;
    private static final int UPGRADED_PLUS_BACKSTAB = 1;


// /STAT DECLARATION/

    public ViciousAssault() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;
        backstabNumber = baseBackstabNumber = BACKSTAB;

        tags.add(ThiefCardTags.BACKSTAB);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1) {

            if (magicNumber != 0) {
                while (magicNumber-- != 0) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                magicNumber = baseMagicNumber;
            }


        } else {
            if (backstabNumber != 0) {
                while (backstabNumber-- != 0) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(
                            m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                }
                backstabNumber = baseBackstabNumber;
            }
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
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            upgradeBackstabNumber(UPGRADED_PLUS_BACKSTAB);
//          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}