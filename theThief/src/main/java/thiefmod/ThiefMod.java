package thiefmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import thiefmod.cards.stolen.modSynergy.disciple.rareFind.StolenClock;
import thiefmod.cards.stolen.modSynergy.halation.rareFind.StolenMail;
import thiefmod.cards.stolen.modSynergy.mystic.rareFind.stolenMysticalOrb;
import thiefmod.cards.stolen.modSynergy.mystic.*;
import thiefmod.cards.stolen.modSynergy.theServant.rareFind.StolenKnives;
import thiefmod.characters.TheThief;
import thiefmod.events.BlackMarketTraderEvent;
import thiefmod.events.LouseAbuseEvent;
import thiefmod.events.MasqueradeEvent;
import thiefmod.patches.character.AbstractCardEnum;
import thiefmod.patches.character.TheThiefEnum;
import thiefmod.relics.*;
import thiefmod.util.IDCheckDontTouchPls;
import thiefmod.variabls.BackstabBlock;
import thiefmod.variabls.BackstabDamage;
import thiefmod.variabls.BackstabMagicNumber;
import thiefmod.variabls.ThiefSecondMagicNumber;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@SpireInitializer
public class ThiefMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private static String modID;
    
    // Check for crossover mods.
    public static final boolean hasConspire;
    public static final boolean hasMysticMod;
    public static final boolean hasHalation;
    public static final boolean hasDisciple;
    public static final boolean hasServant;
    //public static final boolean hasGatherer;
    //public static final boolean hasSlimebound;
    //public static final boolean hasClockwork;
    //public static final boolean hasMarisa?;
    public static final boolean hasHubris;
    public static final boolean hasReplayTheSpire;
    public static final boolean hasInfiniteSpire;
    
    static {
        hasConspire = Loader.isModLoaded("conspire");
        if (hasConspire) {
            logger.info("Detected conspire");
        }
        hasMysticMod = Loader.isModLoaded("MysticMod");
        if (hasMysticMod) {
            logger.info("Detected Mystic Mod");
        }
        hasHalation = Loader.isModLoaded("Halation");
        if (hasHalation) {
            logger.info("Detected Halation");
        }
        hasDisciple = Loader.isModLoaded("chronomuncher");
        if (hasDisciple) {
            logger.info("Detected the Disciple");
        }
        hasServant = Loader.isModLoaded("StS-BlackRuse");
        if (hasServant) {
            logger.info("Detected the Servant");
        }
        
        
        hasReplayTheSpire = Loader.isModLoaded("ReplayTheSpireMod");
        if (hasReplayTheSpire) {
            logger.info("Detected Replay The Spire");
        }
        hasHubris = Loader.isModLoaded("hubris");
        if (hasHubris) {
            logger.info("Detected Hubris");
        }
        hasInfiniteSpire = Loader.isModLoaded("infinitespire");
        if (hasInfiniteSpire) {
            logger.info("Detected Infinite Spire");
        }
    }
    
    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "Thief Mod";
    private static final String AUTHOR = "Gremious";
    private static final String DESCRIPTION = "A mod for Slay the Spire that adds the Thief as a playable character!";
    
    // Color
    public static final Color THIEF_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    
    // Card backgrounds
    private static final String THIEF_GRAY_ATTACK_BG_SMALL = "theThiefAssets/images/512/bg_attack_thief_gray.png";
    private static final String THIEF_GRAY_SKILL_BG_SMALL = "theThiefAssets/images/512/bg_skill_thief_gray.png";
    private static final String THIEF_GRAY_POWER_BG_SMALL = "theThiefAssets/images/512/bg_power_thief_gray.png";
    private static final String THIEF_GRAY_ENERGY_ORB_SMALL = "theThiefAssets/images/512/card_thief_gray_orb.png";
    
    private static final String THIEF_GRAY_ATTACK_BG_LARGE = "theThiefAssets/images/1024/bg_attack_thief_gray.png";
    private static final String THIEF_GRAY_SKILL_BG_LARGE = "theThiefAssets/images/1024/bg_skill_thief_gray.png";
    private static final String THIEF_GRAY_POWER_BG_LARGE = "theThiefAssets/images/1024/bg_power_thief_gray.png";
    private static final String THIEF_GRAY_ENERGY_ORB_LARGE = "theThiefAssets/images/1024/card_thief_gray_orb.png";
    
    private static final String THIEF_GRAY_CARD_ENERGY_ORB = "theThiefAssets/images/512/card_small_orb.png";
    
    // Character assets
    private static final String THE_THIEF_BUTTON = "theThiefAssets/images/charSelect/thiefCharacterButton.png";
    private static final String THE_THIEF_PORTRAIT = rollBGImage();
    public static final String THE_THIEF_SHOULDER_1 = "theThiefAssets/images/char/thiefCharacter/shoulder.png";
    public static final String THE_THIEF_SHOULDER_2 = "theThiefAssets/images/char/thiefCharacter/shoulder2.png";
    public static final String THE_THIEF_CORPSE = "theThiefAssets/images/char/thiefCharacter/corpse.png";
    
    // Mod Badge
    public static final String BADGE_IMAGE = "theThiefAssets/images/Badge.png";
    
    // Animations atlas and JSON files
    public static final String THE_DEFAULT_SKELETON_ATLAS = "theThiefAssets/images/char/thiefCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "theThiefAssets/images/char/thiefCharacter/skeleton.json";
    
    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================
    
    public ThiefMod() {
        logger.info("Subscribe to basemod hooks");
        BaseMod.subscribe(this);
        logger.info("Done subscribing");
        
        setModID("theThief");
        
        logger.info("Creating the color " + AbstractCardEnum.THIEF_GRAY.toString());
        BaseMod.addColor(AbstractCardEnum.THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY, THIEF_GRAY_ATTACK_BG_SMALL, THIEF_GRAY_SKILL_BG_SMALL, THIEF_GRAY_POWER_BG_SMALL, THIEF_GRAY_ENERGY_ORB_SMALL, THIEF_GRAY_ATTACK_BG_LARGE, THIEF_GRAY_SKILL_BG_LARGE, THIEF_GRAY_POWER_BG_LARGE, THIEF_GRAY_ENERGY_ORB_LARGE, THIEF_GRAY_CARD_ENERGY_ORB);
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
    
    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");
        
        logger.info("add " + TheThiefEnum.THE_THIEF.toString());
        
        BaseMod.addCharacter(new TheThief("the Thief", TheThiefEnum.THE_THIEF), THE_THIEF_BUTTON, THE_THIEF_PORTRAIT, TheThiefEnum.THE_THIEF);
        
        logger.info("done editing characters");
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== LOAD THE MOD BADGE AND MENU =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Load Badge Image and mod options");
        
        Texture badgeTexture = new Texture(BADGE_IMAGE);
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
        BaseMod.addRelicToCustomPool(new StolenBag(), AbstractCardEnum.THIEF_GRAY);
        
        // All-Classes:
        BaseMod.addRelic(new PocketChange(), RelicType.SHARED);
        BaseMod.addRelic(new ShadowBoots(), RelicType.SHARED);
        BaseMod.addRelic(new LouseBounty(), RelicType.SHARED);
        BaseMod.addRelic(new BottledHand(), RelicType.SHARED);
        
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
        } catch (URISyntaxException | IllegalAccessException | InstantiationException | NotFoundException | CannotCompileException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        //  BaseMod.addCard(new ());
        if (hasDisciple) BaseMod.addCard(new StolenClock());
        if (hasHalation) BaseMod.addCard(new StolenMail());
        if (hasMysticMod) {
            BaseMod.addCard(new stolenArteScroll());
            BaseMod.addCard(new stolenBookOfArte());
            
            BaseMod.addCard(new stolenMagicCantrip());
            BaseMod.addCard(new stolenBagOfMagicCantrips());
            
            BaseMod.addCard(new stolenSpellScroll());
            BaseMod.addCard(new stolenMysticalSpellbook());
            
            BaseMod.addCard(new stolenMysticalOrb());
        }
        if (hasServant) {
            BaseMod.addCard(new StolenKnives());
        }
        logger.info("Cards - added!");
    }
    
    private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, CannotCompileException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = ThiefMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));
        
        ClassFilter filter = new AndClassFilter(new NotClassFilter(new InterfaceOnlyClassFilter()), new NotClassFilter(new AbstractClassFilter()), new ClassModifiersClassFilter(Modifier.PUBLIC), new CardFilter() // Make sure to edit the card filter to your own packaging structure. Cards outside of the filter will not be loaded.
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
            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();
            BaseMod.addCard(card);
            if (cls.hasAnnotation(CardNoSeen.class)) { // By default, all cards are seen, If you want to mark a card as unseen use @CardNoSeen
                UnlockTracker.hardUnlockOverride(card.cardID);
            } else {
                UnlockTracker.unlockCard(card.cardID);
            }
        }
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Begin Editing Strings");
        
        // Regular Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Assets/localization/eng/cards/ThiefMod-Card-Strings.json");
        
        // Stolen Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Assets/localization/eng/cards/ThiefMod-Stolen-Card-Strings.json");
        
        // Backstab Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Assets/localization/eng/cards/ThiefMod-Backstab-Card-Strings.json");
        
        // Curse Cards CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, getModID() + "Assets/localization/eng/cards/ThiefMod-Curse-Card-Strings.json");
        
        // character Strings
        BaseMod.loadCustomStringsFile(CharacterStrings.class, getModID() + "Assets/localization/eng/ThiefMod-Character-Strings.json");
        
        // Power Strings
        BaseMod.loadCustomStringsFile(PowerStrings.class, getModID() + "Assets/localization/eng/ThiefMod-Power-Strings.json");
        
        // Relic Strings
        BaseMod.loadCustomStringsFile(RelicStrings.class, getModID() + "Assets/localization/eng/ThiefMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class, getModID() + "Assets/localization/eng/ThiefMod-Event-Strings.json");
        
        // UI Strings
        BaseMod.loadCustomStringsFile(UIStrings.class, getModID() + "Assets/localization/eng/ThiefMod-UI-Strings.json");
        
        logger.info("Done Edtting Strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal("theThiefAssets/localization/eng/ThiefMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    // ================ /LOAD THE KEYWORDS/ ===================
    
    private static final String rollBGImage() {
        Random rand = new Random();
        int i = rand.nextInt(99);
        if (i < 90) {
            return "theThiefAssets/images/charSelect/thiefCharacterPortraitBG.png";
        } else {
            logger.info("Spooky.");
            return "theThiefAssets/images/charSelect/thiefCharacterPortraitEvilBG.png";
        }
    }
    
    // ====== NO EDIT AREA ======
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ThiefMod.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStrings.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ThiefMod.class.getResourceAsStream("/IDCheckStrings.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        
        String packageName = ThiefMod.class.getPackage().getName(); // STILL NOT EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    // ====== YOU CAN EDIT AGAIN ======
    
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}

