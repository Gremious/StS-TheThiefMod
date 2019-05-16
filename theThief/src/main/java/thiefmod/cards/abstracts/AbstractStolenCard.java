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

import static com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.*;

@CardIgnore
public abstract class AbstractStolenCard extends AbstractThiefCard {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("theThief:TooltipNames");
    public static final String[] FLAVOR_STRINGS = uiStrings.TEXT;
    public CardRarity subRarity;
    
    public AbstractStolenCard(final String id, final String img, final int cost, final CardType type, final CardTarget target,
                              CardRarity subRarity, AbstractPlayer.PlayerClass character) {
        super(id, img, cost, type, CardColor.COLORLESS, CardRarity.SPECIAL, target);
        this.subRarity = subRarity;
        tags.add(ThiefCardTags.STOLEN);
        setBgImage(character, type, subRarity);
    }
    
    private void setBgImage(AbstractPlayer.PlayerClass character, CardType type, CardRarity subRarity) {
        if (character == THE_SILENT || character == IRONCLAD || character == DEFECT) {
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
        } else if (ThiefMod.hasMysticMod) {
            if (character == MysticEnum.MYSTIC_CLASS) {
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_mystic.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_mystic.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_mystic.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_mystic.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_mystic.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_mystic.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_mystic.png");
                                break;
                        }
                }
            }
        } else if (ThiefMod.hasServant) {
            if (character == TheServantEnum.THE_SERVANT) {
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_servant.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_servant.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_servant.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_servant.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_servant.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_servant.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_servant.png");
                                break;
                        }
                }
            }
        } else if (ThiefMod.hasDisciple) {
            if (character == chronomuncher.patches.Enum.CHRONO_CLASS) {
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_disciple.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_disciple.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_disciple.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_disciple.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_disciple.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_disciple.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_disciple.png");
                                break;
                        }
                }
            }
        } else if (ThiefMod.hasBard) {
            if (character == Bard.Enums.BARD) {
                switch (subRarity) {
                    case RARE:
                        setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                                "theThiefAssets/images/cardui/1024/banner_rare.png");
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_bard.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_bard.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_bard.png");
                                break;
                        }
                        break;
                    default:
                        switch (type) {
                            case ATTACK:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_attack_stolen_bard.png");
                                break;
                            case SKILL:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_skill_stolen_bard.png");
                                break;
                            case POWER:
                                setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_bard.png",
                                        "theThiefAssets/images/cardui/1024/bg_power_stolen_bard.png");
                                break;
                        }
                }
            }
        } else {
            switch (subRarity) {
                case RARE:
                    setBannerTexture("theThiefAssets/images/cardui/512/banner_rare.png",
                            "theThiefAssets/images/cardui/1024/banner_rare.png");
                    switch (type) {
                        case ATTACK:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_attack_stolen_thief.png");
                            break;
                        case SKILL:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_skill_stolen_thief.png");
                            break;
                        case POWER:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_power_stolen_thief.png");
                            break;
                    }
                    break;
                default:
                    switch (type) {
                        case ATTACK:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_attack_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_attack_stolen_thief.png");
                            break;
                        case SKILL:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_skill_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_skill_stolen_thief.png");
                            break;
                        case POWER:
                            setBackgroundTexture("theThiefAssets/images/cardui/512/bg_power_stolen_thief.png",
                                    "theThiefAssets/images/cardui/1024/bg_power_stolen_thief.png");
                            break;
                    }
            }
        }
    }
    
    protected static String loadLockedCardImage(CardType type) {
        switch (type) {
            case ATTACK:
                return "theThiefAssets/images/cards/locked_attack.png";
            case POWER:
                return "theThiefAssets/images/cards/locked_power.png";
            default:
                return "theThiefAssets/images/cards/locked_skill.png";
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