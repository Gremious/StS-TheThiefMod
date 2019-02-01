package thiefmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.RoomTintEffect;
import thiefmod.ThiefMod;
import thiefmod.patches.Character.AbstractCardEnum;

import static com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED;
import static com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH;

public class Murder extends AbstractBackstabCard {


// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("Murder.");
    public static final String IMG = ThiefMod.makePath(ThiefMod.DEFAULT_COMMON_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:FlavorText");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 3;

    private static final int DAMAGE = 100;
    private static final int UPGRADE_PLUS_DAMAGE = 120;


// /STAT DECLARATION/

    public Murder() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        FleetingField.fleeting.set(this, true);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage * this.backstabNumber, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(new ShakeScreenAction(1.0f, MED, HIGH));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(
                new RoomTintEffect(Color.RED, 0.2f), 1.0f));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(
                new AdditiveSlashImpactEffect(m.drawX, m.drawY, Color.RED)));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.cardsPlayedThisTurn == 0) {
            this.rawDescription = this.DESCRIPTION + this.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = this.DESCRIPTION + this.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
//          this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}