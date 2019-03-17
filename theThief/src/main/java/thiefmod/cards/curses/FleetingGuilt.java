package thiefmod.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.relics.BlueCandle;
import thiefmod.CardNoSeen;
import thiefmod.ThiefMod;
import thiefmod.cards.AbstractBackstabCard;

@CardNoSeen
public class FleetingGuilt extends AbstractBackstabCard {

// TEXT DECLARATION

    public static final String ID = ThiefMod.makeID("FleetingGuilt");
    public static final String IMG = "theThiefAssets/images/cards/beta/FleetingGuilt.png";
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

    public FleetingGuilt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        GraveField.grave.set(this, true);

        isEthereal = true; // I'll be honest idk which one I need I can't be bothered to check. I think exhaust.
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!dontTriggerOnUseCard && p.hasRelic(BlueCandle.ID)) {
            useBlueCandle(p);
        } else {
            action(new ApplyPowerAction(
                    p, p, new LoseDexterityPower(p, 1)));
        }

    }

    @Override
    public void triggerWhenDrawn() {
        action(new SetDontTriggerAction(this, false));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
    }

}