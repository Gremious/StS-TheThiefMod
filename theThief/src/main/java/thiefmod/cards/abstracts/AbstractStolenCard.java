package thiefmod.cards.abstracts;

import blackrusemod.patches.TheServantEnum;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.bard.characters.Bard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.CardFlashVfx;
import mysticmod.patches.MysticEnum;
import thiefmod.CardIgnore;
import thiefmod.ThiefMod;
import thiefmod.patches.character.ThiefCardTags;

@CardIgnore
public abstract class AbstractStolenCard extends AbstractThiefCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    private CardRarity subRarity;
    
    public AbstractStolenCard(final String id, final String img, final int cost, final CardType type, final CardTarget target,
                              CardRarity subRarity, AbstractPlayer.PlayerClass character) {
        super(id, img, cost, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.subRarity = subRarity;
        tags.add(ThiefCardTags.STOLEN);
        setBgImage(character, type, subRarity);
    }
    
    private void setBgImage(AbstractPlayer.PlayerClass character, CardType type, CardRarity subRarity) {
        switch (character) {
            case THE_SILENT:
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_silent.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_silent.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_silent_rare.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_silent_rare.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_silent.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_silent.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_silent.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_silent.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_silent_rare.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_silent_rare.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_silent.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_silent.png");
                                break;
                        }
                        break;
                }
                break;
            case IRONCLAD:
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_ironclad.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_ironclad.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_ironclad.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_ironclad.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_ironclad.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_ironclad.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_ironclad.png");
                                break;
                        }
                        break;
                }
                break;
            case DEFECT:
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_defect.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_defect.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_defect.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_defect.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_defect.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_defect.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_defect.png");
                                break;
                        }
                }
                break;
        }
        if (ThiefMod.hasMysticMod) {
            if (character == MysticEnum.MYSTIC_CLASS) {
            
            }
        }
        if (ThiefMod.hasServant) {
            if (character == TheServantEnum.THE_SERVANT) {
            
            }
        }
        if (ThiefMod.hasDisciple) {
            if (character == chronomuncher.patches.Enum.CHRONO_CLASS) {
            
            }
        }
        if (ThiefMod.hasBard) {
            if (character == Bard.Enums.BARD) {
            
            }
        }
    }
    
    @Override
    public void triggerWhenDrawn() {
        if (subRarity == CardRarity.RARE) {
            AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));
        }
    }
    
    @Override
    public void triggerWhenCopied() {
        if (subRarity == CardRarity.RARE) {
            AbstractDungeon.effectList.add(new CardFlashVfx(this, Color.GOLD));
        }
    }
    
    //==
    public static String flavortext(String EXTENDED_DESCRIPTION) {
        return EXTENDED_DESCRIPTION;
    }
}