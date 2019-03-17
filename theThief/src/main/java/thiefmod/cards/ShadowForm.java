package thiefmod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thiefmod.cards.abstracts.AbstractBackstabCard;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.ThiefCardTags;
import thiefmod.powers.Unique.ShadowFormPower;

public class ShadowForm extends AbstractBackstabCard {
    // TEXT DECLARATION
    
    public static final String ID = thiefmod.ThiefMod.makeID("ShadowForm");
    public static final String IMG = "theThiefAssets/images/cards/ShadowForm2.png";
    public static final CardColor COLOR = AbstractCardEnum.THIEF_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    
    private static final int COST = 3;
    private static final int MAGIC = 1;
    private static final int BACKSTAB = 2;
    // /STAT DECLARATION/
    
    public ShadowForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        isEthereal = true;
        magicNumber = baseMagicNumber = MAGIC;
        baseBackstabNumber = backstabNumber = BACKSTAB;
        tags.add(ThiefCardTags.BACKSTAB);
        tags.add(BaseModCardTags.FORM);
        initializeDescription();
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (canBackstab()) {
            act(new ApplyPowerAction(p, p, new ShadowFormPower(p, backstabNumber), backstabNumber));
        } else {
            act(new ApplyPowerAction(p, p, new ShadowFormPower(p, magicNumber), magicNumber));
        }
    }
    
    @Override
    public String flavortext() {
        return EXTENDED_DESCRIPTION[0];
    }
    
    @Override
    public void applyPowers() {
        super.applyPowers();
        if (canBackstab()) {
            if (magicNumber == 1) {
                if (!upgraded) {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[5];
                }
            } else {
                if (!upgraded) {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[3];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[5];
                }
            }
        } else {
            if (magicNumber == 1) {
                if (!upgraded) {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[4];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[6];
                }
            } else {
                if (!upgraded) {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[4];
                } else {
                    rawDescription = EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[6];
                }
            }
        }
        initializeDescription();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            isEthereal = false;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}