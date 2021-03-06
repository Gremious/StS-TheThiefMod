package thiefmod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.ThiefMod;
import thiefmod.cards.DefendThief;
import thiefmod.cards.Lie;
import thiefmod.cards.Steal;
import thiefmod.cards.StrikeThief;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.relics.ThievesMask;

import java.util.ArrayList;

import static thiefmod.ThiefMod.THE_DEFAULT_SKELETON_ATLAS;
import static thiefmod.ThiefMod.THE_DEFAULT_SKELETON_JSON;

public class TheThief extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());

    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int CURRENT_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 80;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== STRINGS =================

    private static final String ID = ThiefMod.makeID("ThiefCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================

    public static final float[] LAYERSPEED = {20.0f, 0.0f, -40.0f, 40.0f, 0.0f};

    // =============== /BASE STATS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {

            "theThiefAssets/images/char/thiefCharacter/orb/layer5.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer4.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer3.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer2.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer1.png",

            "theThiefAssets/images/char/thiefCharacter/orb/layer6.png",

            "theThiefAssets/images/char/thiefCharacter/orb/layer5d.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer4d.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer3d.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer2d.png",
            "theThiefAssets/images/char/thiefCharacter/orb/layer1d.png",
    };
    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============


// =============== CHARACTER CLASS START =================

    public TheThief(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, "theThiefAssets/images/char/thiefCharacter/orb/vfx.png", LAYERSPEED,
                new SpriterAnimation("theThiefAssets/images/char/thiefCharacter/Spriter/thief8000.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout
                thiefmod.ThiefMod.THE_THIEF_SHOULDER_1,        // campfire pose
                thiefmod.ThiefMod.THE_THIEF_SHOULDER_2,        // another campfire pose
                thiefmod.ThiefMod.THE_THIEF_CORPSE,            // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));    // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        this.loadAnimation(THE_DEFAULT_SKELETON_ATLAS, THE_DEFAULT_SKELETON_JSON, 1.0f);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        // =============== /TEXT BUBBLE LOCATION/ =================


    }

    // =============== /CHARACTER CLASS END/ =================


    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                CURRENT_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);

    }


    // Startin  g Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading started Deck strings");

        retVal.add(StrikeThief.ID);
        retVal.add(StrikeThief.ID);
        retVal.add(StrikeThief.ID);
        retVal.add(StrikeThief.ID);

        retVal.add(DefendThief.ID);
        retVal.add(DefendThief.ID);
        retVal.add(DefendThief.ID);
        retVal.add(DefendThief.ID);

        retVal.add(Lie.ID);

        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ThievesMask.ID);

        UnlockTracker.markRelicAsSeen(ThievesMask.ID);

        return retVal;
    }

    // character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); // Screen Effect
    }

    // character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.THIEF_GRAY;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return thiefmod.ThiefMod.THIEF_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        FontHelper.energyNumFontRed.setColor(50.0f, 50.0f, 50.0f, 250.0f);
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which starting card should specific events give you?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Steal();
    }

    // The class name as it appears next to your player name in game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending this.name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheThief(this.name, this.chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return thiefmod.ThiefMod.THIEF_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return thiefmod.ThiefMod.THIEF_GRAY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in damage act and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};

    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
