package thiefmod.cards.stolen.modSynergy.bard;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.SelectMelodyAction;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.Doot;
import com.evacipated.cardcrawl.mod.bard.cards.InspiringSong;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;

public class StolenFlute extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenFlute");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    
    private static final int MAGIC = 1;
    private static final int UPGRADED_MAGIC = 2;
    
    
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    public StolenFlute() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, Bard.Enums.BARD);
        magicNumber = baseMagicNumber = MAGIC;
        if (ThiefMod.hasBard) {
            portrait = new Doot().portrait;
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            act(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));
            act(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber));
        }
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasBard) {
            return AbstractBardCard.getPortraitImage(new Doot());
        } else {
            return super.getPortraitImage();
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPGRADED_MAGIC);
            upgradeName();
            
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}