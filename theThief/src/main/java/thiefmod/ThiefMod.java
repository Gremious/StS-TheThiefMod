package thiefmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.clapper.util.classutil.*;
import thiefmod.characters.TheThief;
import thiefmod.events.BlackMarketTraderEvent;
import thiefmod.events.LouseAbuseEvent;
import thiefmod.events.MasqueradeEvent;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.patches.Character.TheThiefEnum;
import thiefmod.relics.*;
import thiefmod.variabls.BackstabBlock;
import thiefmod.variabls.BackstabDamage;
import thiefmod.variabls.BackstabMagicNumber;
import thiefmod.variabls.ThiefSecondMagicNumber;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
// Note: #y b r g p

@SpireInitializer
public class ThiefMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {

    // Logger
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private int LogInt = 0;

    // Check for crossover mods.
    public static final boolean hasConspire;
    public static final boolean hasHubris;
    public static final boolean hasReplayTheSpire;
    public static final boolean hasInfiniteSpire;
    public static final boolean hasDisciple;
    public static final boolean hasMysticMod;
    public static final boolean hasBeaked;

    static {
        hasConspire = Loader.isModLoaded("conspire");
        if (hasConspire) {
            logger.info("Detected Replay The Spire");
        }
        hasReplayTheSpire = Loader.isModLoaded("ReplayTheSpireMod");
        if (hasReplayTheSpire) {
            logger.info("Detected Replay The Spire");
        }
        hasHubris = Loader.isModLoaded("hubris");
        if (hasHubris) {
            logger.info("Detected Hubris");
        }
        hasDisciple = Loader.isModLoaded("constructmod");
        if (hasDisciple) {
            logger.info("Detected ConstructMod");
        }
        hasInfiniteSpire = Loader.isModLoaded("infinitespire");
        if (hasInfiniteSpire) {
            logger.info("Detected Infinite Spire");
        }
        hasMysticMod = Loader.isModLoaded("MysticMod");
        if (hasMysticMod) {
            logger.info("Detected Mystic Mod");
        }
        hasBeaked = Loader.isModLoaded("MysticMod");
        if (hasBeaked) {
            logger.info("Detected Beaked Mod");
        }
    }

    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "Thief Mod";
    private static final String AUTHOR = "Gremious";
    private static final String DESCRIPTION = "A mod for Slay the Spire that adds the Thief as a playable character!";

    // =============== IMPUT TEXTURE LOCATION =================

    // Color
    public static final Color THIEF_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);

    // Image folder name
    private static final String THIEF_MOD_ASSETS_FOLDER = "thiefmodAssets/images";

    // card backgrounds
    private static final String ATTACK_DEAFULT_GRAY = "512/bg_attack_thief_gray.png";
    private static final String POWER_DEAFULT_GRAY = "512/bg_power_thief_gray.png";
    private static final String SKILL_DEAFULT_GRAY = "512/bg_skill_thief_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY = "512/card_thief_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";

    private static final String ATTACK_DEAFULT_GRAY_PORTRAIT = "1024/bg_attack_thief_gray.png";
    private static final String POWER_DEAFULT_GRAY_PORTRAIT = "1024/bg_power_thief_gray.png";
    private static final String SKILL_DEAFULT_GRAY_PORTRAIT = "1024/bg_skill_thief_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY_PORTRAIT = "1024/card_thief_gray_orb.png";

    // Card images

    public static final String DEFAULT_COMMON_ATTACK = "cards/beta/Attack.png";
    public static final String DEFAULT_COMMON_SKILL = "cards/beta/Skill.png";
    public static final String DEFAULT_UNCOMMON_ATTACK = "cards/beta/Attack.png";
    public static final String DEFAULT_UNCOMMON_SKILL = "cards/beta/Skill.png";
    public static final String DEFAULT_UNCOMMON_POWER = "cards/beta/Power.png";


    // Power images
    public static final String COMMON_POWER = "powers/placeholder_power.png";
    public static final String RARE_POWER = "powers/placeholder_power.png";


    // Character assets
    private static final String THE_THIEF_BUTTON = "charSelect/thiefCharacterButton.png";
    private static final String THE_THIEF_PORTRAIT = rollBGImage();
    public static final String THE_THIEF_SHOULDER_1 = "char/thiefCharacter/shoulder.png";
    public static final String THE_THIEF_SHOULDER_2 = "char/thiefCharacter/shoulder2.png";
    public static final String THE_THIEF_CORPSE = "char/thiefCharacter/corpse.png";

    //Mod Badge
    public static final String BADGE_IMAGE = "Badge.png";

    // Animations atlas and JSON files
    public static final String THE_DEFAULT_SKELETON_ATLAS = "char/thiefCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "char/thiefCharacter/skeleton.json";


    // =============== /IMPUT TEXTURE LOCATION/ =================

    /**
     * Makes a full path for a resource path
     *
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
        return THIEF_MOD_ASSETS_FOLDER + "/" + resource;
    }

    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    public ThiefMod() {
        logger.info("Subscribe to basemod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");


        logger.info("Creating the color " + AbstractCardEnum.THIEF_GRAY.toString());

        BaseMod.addColor(AbstractCardEnum.THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY,
                makePath(ATTACK_DEAFULT_GRAY), makePath(SKILL_DEAFULT_GRAY), makePath(POWER_DEAFULT_GRAY), makePath(ENERGY_ORB_DEAFULT_GRAY),
                makePath(ATTACK_DEAFULT_GRAY_PORTRAIT), makePath(SKILL_DEAFULT_GRAY_PORTRAIT), makePath(POWER_DEAFULT_GRAY_PORTRAIT),
                makePath(ENERGY_ORB_DEAFULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

        logger.info("Done Creating the color");
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= The Thief is sneaking in! =========================");
        ThiefMod thiefmod = new ThiefMod();
        logger.info("========================= /Thief snuk in./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================
    private static final String rollBGImage() {
        String PORTRAIT;

        Random rand = new Random();
        int i = rand.nextInt(99);
        if (i < 75) {
            PORTRAIT = "charSelect/thiefCharacterPortraitBG.png";
        } else {
            PORTRAIT = "charSelect/thiefCharacterPortraitEvilBG.png";
        }
        return PORTRAIT;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");

        logger.info("add " + TheThiefEnum.THE_THIEF.toString());

        BaseMod.addCharacter(new TheThief("the Thief", TheThiefEnum.THE_THIEF),
                makePath(THE_THIEF_BUTTON), makePath(THE_THIEF_PORTRAIT), TheThiefEnum.THE_THIEF);

        logger.info("done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================


    // =============== LOAD THE MOD BADGE AND MENU =================

    @Override
    public void receivePostInitialize() {
        logger.info("Load Badge Image and mod options");

        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("The Thief Mod does not have any settings!", 400.0f, 700.0f, settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");
        // Events
        logger.info("Adding Events");

        BaseMod.addEvent(LouseAbuseEvent.ID, LouseAbuseEvent.class, Exordium.ID);
        BaseMod.addEvent(BlackMarketTraderEvent.ID, BlackMarketTraderEvent.class, Exordium.ID);
        BaseMod.addEvent(MasqueradeEvent.ID, MasqueradeEvent.class, TheCity.ID);

        logger.info("Done Adding Events!");
    }


    // =============== /LOAD THE MOD BADGE AND MENU/ =================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        // Thief-Specific:
        BaseMod.addRelicToCustomPool(new ThievesMask(), AbstractCardEnum.THIEF_GRAY);
        BaseMod.addRelicToCustomPool(new ShadowMask(), AbstractCardEnum.THIEF_GRAY);
        BaseMod.addRelicToCustomPool(new ShadowCloak(), AbstractCardEnum.THIEF_GRAY);
        BaseMod.addRelicToCustomPool(new Lockpicks(), AbstractCardEnum.THIEF_GRAY);
        BaseMod.addRelicToCustomPool(new StickyGloves(), AbstractCardEnum.THIEF_GRAY);
        BaseMod.addRelicToCustomPool(new LoadedDice(), AbstractCardEnum.THIEF_GRAY);

        // All-Classes:
        BaseMod.addRelic(new PocketChange(), RelicType.SHARED);
        BaseMod.addRelic(new ShadowBoots(), RelicType.SHARED);
        BaseMod.addRelic(new LouseBounty(), RelicType.SHARED);
        BaseMod.addRelic(new BottledLiver(), RelicType.SHARED);

        logger.info("done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Add variables");
        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new BackstabDamage());
        BaseMod.addDynamicVariable(new BackstabMagicNumber());
        BaseMod.addDynamicVariable(new BackstabBlock());
        BaseMod.addDynamicVariable(new ThiefSecondMagicNumber());

        try {
            autoAddCards();
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | CannotCompileException e) {
            e.printStackTrace();
        }

        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================
    private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, CannotCompileException {
        ClassFinder finder = new ClassFinder();
        URL url = ThiefMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter() // Make sure to edit the card filter to your own packaging structure. I have added stolen cards to filter.
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());
            if (cls.hasAnnotation(CardIgnore.class)) { // Add @CardIgnore to cards you want to ignore from being loaded (super special cards, options, etc).
                continue;
            }
            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }
            System.out.println(classInfo.getClassName());
            AbstractCard card = (AbstractCard) Loader.getClassPool().toClass(cls).newInstance();
            BaseMod.addCard(card);
            if (cls.hasAnnotation(CardNoSeen.class)) { // By default, all cards are seen, If you want to mark a card as unseen use @CardNoSeen
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
                UnlockTracker.unlockCard(card.cardID);
            }
        }
    }

    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin Editing Strings");

        // Regular Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Card-Strings.json");

        // Stolen Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Stolen-Card-Strings.json");

        // Backstab Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Backstab-Card-Strings.json");

        // Curse Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Curse-Card-Strings.json");

        // Character Strings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Character-Strings.json");

        // Power Strings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Power-Strings.json");

        // Relic Strings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-Event-Strings.json");

        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                "thiefmodAssets/localization/eng/ThiefMod-UI-Strings.json");

        logger.info("Done Edtting Strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal("thiefmodAssets/localization/eng/ThiefMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }


    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "theThief:" + idText;
    }

}
