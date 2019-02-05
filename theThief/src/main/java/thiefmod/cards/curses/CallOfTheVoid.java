package thiefmod.cards.curses;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EvolvePower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;

@CardNoSeen
public class CallOfTheVoid extends AbstractBackstabCard {

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("CallOfTheVoid");
    public static final String IMG = "thiefmodAssets/images/cards/CallOfTheVoid.png";
    public static final CardColor COLOR = CardColor.CURSE;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


// /TEXT DECLARATION/

    // STAT DECLARATION
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.CURSE;

    private static final int COST = -2;

    private static final int MAGIC = 1;

// /STAT DECLARATION/

    public CallOfTheVoid() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(magicNumber));
        if (AbstractDungeon.player.hasPower(EvolvePower.POWER_ID) && !AbstractDungeon.player.hasPower(NoDrawPower.POWER_ID)) {
            AbstractDungeon.player.getPower(EvolvePower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.getPower(EvolvePower.POWER_ID).amount));
        }

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {
    }
}