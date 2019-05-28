package thiefmod.cards.stolen.modSynergy.bard;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.bard.actions.common.SelectMelodyAction;
import com.evacipated.cardcrawl.mod.bard.cards.AbstractBardCard;
import com.evacipated.cardcrawl.mod.bard.cards.InspiringSong;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.evacipated.cardcrawl.mod.bard.helpers.MelodyManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.ThiefMod;
import thiefmod.cards.abstracts.AbstractStolenCard;
import thiefmod.patches.character.TheThiefEnum;

public class StolenSong extends AbstractStolenCard {
    
    // TEXT DECLARATION
    public static final String ID = ThiefMod.makeID("StolenSong");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    
    // STAT DECLARATION
    
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    
    // /STAT DECLARATION/
    
    public static final String IMG = loadLockedCardImage(TYPE);
    
    public StolenSong() {
        super(ID, IMG, COST, TYPE, TARGET, CardRarity.COMMON, Bard.Enums.BARD);
        if (ThiefMod.hasBard) {
            portrait = new InspiringSong().portrait;
        }
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SelectMelodyAction(MelodyManager.getAllMelodies(), false));
    }
    
    @Override
    protected Texture getPortraitImage() {
        if (ThiefMod.hasBard) {
            return AbstractBardCard.getPortraitImage(new InspiringSong());
        } else {
            return super.getPortraitImage();
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeBaseCost(UPGRADE_COST);
            upgradeName();
            //          rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}