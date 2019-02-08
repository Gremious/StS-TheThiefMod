package thiefmod.cards.backstab;

import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Common.BackstabPower;

import java.util.ArrayList;
import java.util.List;

public class PerfectDagger extends AbstractBackstabCard {


    // TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("PerfectDagger");
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
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    private static int MISC = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADED_PLUS_MAGIC = 1;


// /STAT DECLARATION/


    public PerfectDagger() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        misc = MISC;
        baseMagicNumber = MAGIC;

        magicNumber = baseMagicNumber;
        damage = baseDamage = misc;

        tags.add(ThiefCardTags.BACKSTAB);

        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        final int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

        if (count <= 1) {

            AbstractDungeon.actionManager.addToBottom(
                    new IncreaseMiscAction(uuid, misc, magicNumber));
        }
        if (damage < 19) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(
                    m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        } else if (damage >= 19 && damage < 35) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(
                    m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DAGGER_1"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DamageImpactCurvyEffect(m.drawX, m.drawY)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(
                    m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DAGGER_5"));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DAGGER_5"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DamageImpactCurvyEffect(m.drawX, m.drawY)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DamageImpactLineEffect(m.drawX, m.drawY)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new AdditiveSlashImpactEffect(m.drawX, m.drawY, Color.RED)));
        }
    }


    @Override
    public void applyPowers() {
        baseDamage = misc;
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

    @Override
    public void triggerOnEndOfPlayerTurn() {
        rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
        super.triggerOnEndOfPlayerTurn();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
            initializeDescription();
        }
    }
}