package thiefmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.powers.Unique.DramaticFeignPower;

public class DramaticFeign extends AbstractBackstabCard {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    /*
     * 3 (2) Mana, Rare, Skill Apply 2 vulnerable and stun all enemies. You cannot
     * play cards for the rest of this turn. Next turn, the first card you play is
     * free.
     */


// TEXT DECLARATION 

    public static final String ID = thiefmod.ThiefMod.makeID("DramaticFeign");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String FLAVOR_STRINGS[] = uiStrings.TEXT;
    public static final String IMG = "theThiefAssets/images/cards/beta/DramaticFeign.png";


    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION[] = cardStrings.EXTENDED_DESCRIPTION;

// /TEXT DECLARATION/


    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = AbstractCardEnum.THIEF_GRAY;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;

    private int MAGIC = 2;

    private int AMOUNT = 1;
// /STAT DECLARATION/

    public DramaticFeign() {
        super(ID,  IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;

        logger.info("Initialized Method");

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (final AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {

            act(new StunMonsterAction(mo, p));

            act(new ApplyPowerAction(mo, p,
                    new VulnerablePower(mo, magicNumber, false), magicNumber));
        }

        act(
                new ApplyPowerAction(p, p, new DramaticFeignPower(p, p, AMOUNT), AMOUNT));


        // VFX
        act(new VFXAction(new GrandFinalEffect(), 1.0f));
    }

    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}