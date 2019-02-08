package thiefmod.cards;

import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.PetalEffect;
import thiefmod.ThiefMod;
import thiefmod.patches.character.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

public class BeautifulLies extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("BeautifulLies");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");

    public static final String IMG = "thiefmodAssets/images/cards/BeautifulLies.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String NAME = cardStrings.NAME;

    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;

    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DAMAGE = 2;


// /STAT DECLARATION/

    public BeautifulLies() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        baseDamage = DAMAGE;
        exhaust = true;
        SoulboundField.soulbound.set(this, true);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, damage, damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        AbstractDungeon.effectList.add(new PetalEffect());

        AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(makeStatEquivalentCopy()));

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
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
//          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}