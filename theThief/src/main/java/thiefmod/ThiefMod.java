package thiefmod;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.green.CloakAndDagger;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thiefmod.cards.*;
import thiefmod.characters.TheThief;
import thiefmod.patches.Character.AbstractCardEnum;
import thiefmod.patches.Character.TheThiefEnum;
import thiefmod.relics.ThievesMask;
import thiefmod.variabls.BackstabBlock;
import thiefmod.variabls.BackstabDamage;
import thiefmod.variabls.BackstabMagicNumber;
import thiefmod.variabls.ThiefSecondMagicNumber;

// Note: #y b r g p

@SpireInitializer
public class ThiefMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {
    public static final Logger logger = LogManager.getLogger(ThiefMod.class.getName());
    private int LogInt = 0;

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
    public static final String DEFAULT_COMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_COMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_COMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_UNCOMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_UNCOMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_UNCOMMON_POWER = "cards/Power.png";
    public static final String DEFAULT_RARE_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_RARE_SKILL = "cards/Skill.png";
    public static final String DEFAULT_RARE_POWER = "cards/Power.png";


    public static final String DRAMATIC_FEIGN = "cards/DramaticFeign.png";
    public static final String SHADOW_FORM = "cards/ShadowForm.png";

    // Power images
    public static final String COMMON_POWER = "powers/placeholder_power.png";
    public static final String UNCOMMON_POWER = "powers/placeholder_power.png";
    public static final String RARE_POWER = "powers/placeholder_power.png";


    // Relic images
    public static final String PLACEHOLDER_RELIC = "relics/placeholder_relic.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE = "relics/outline/placeholder_relic.png";
    // Character assets
    private static final String THE_THIEF_BUTTON = "charSelect/thiefCharacterButton.png";
    private static final String THE_THIEF_PORTRAIT = "charSelect/thiefCharacterPortraitBG.png";
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
        ThiefMod defaultmod = new ThiefMod();
        logger.info("========================= /Thief snuk in./ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================

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

        logger.info("Done loadiing badge Image and mod options");

    }

    // =============== /LOAD THE MOD BADGE AND MENU/ =================


    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        BaseMod.addRelicToCustomPool(new ThievesMask(), AbstractCardEnum.THIEF_GRAY);

        logger.info("done adding relics!");
    }

    // ================ /ADD RELICS/ ===================


    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new BackstabDamage());
        BaseMod.addDynamicVariable(new BackstabMagicNumber());
        BaseMod.addDynamicVariable(new BackstabBlock());
        BaseMod.addDynamicVariable(new ThiefSecondMagicNumber());


        logger.info("Add Cards");
        logger.info(LogInt++);
        // Add the cards
        BaseMod.addCard(new StrikeThief());
        logger.info(LogInt++);
        BaseMod.addCard(new Steal());
        logger.info(LogInt++);
        BaseMod.addCard(new DefendThief());
        logger.info(LogInt++);
        //    BaseMod.addCard(new GuiltTrip());


        BaseMod.addCard(new FeignTrip());
        BaseMod.addCard(new ThousandBallBearings());
        BaseMod.addCard(new LieInWait());
        BaseMod.addCard(new Multitask());
        BaseMod.addCard(new Gaslight());
        BaseMod.addCard(new Reobtain());
        BaseMod.addCard(new SteakOut());
        BaseMod.addCard(new ShadowReserves());
        BaseMod.addCard(new PaydayLoan());
        BaseMod.addCard(new HeartBreaker());
        BaseMod.addCard(new PerfectDagger());
        BaseMod.addCard(new ShadowCalamity());
        BaseMod.addCard(new Ambush());
        BaseMod.addCard(new ViciousAssault());
        BaseMod.addCard(new DirtyDeedsDDC());
        BaseMod.addCard(new AttackOfOpportunity());
        BaseMod.addCard(new Mercy());
        BaseMod.addCard(new Darts());
        BaseMod.addCard(new DoubleDealing());
        BaseMod.addCard(new Prep());
        BaseMod.addCard(new ShadowEvade());
        BaseMod.addCard(new OneStepAhead());
        BaseMod.addCard(new FollowUp());
        BaseMod.addCard(new Exploit());
        BaseMod.addCard(new BeautifulLies());
        BaseMod.addCard(new SimilarSkills());
        BaseMod.addCard(new Murder());
        BaseMod.addCard(new SwiftTread());
        BaseMod.addCard(new DramaticFeign());
        BaseMod.addCard(new ShadowForm());
        BaseMod.addCard(new Stab());
        BaseMod.addCard(new CloakAndDaggerThief());
        BaseMod.addCard(new Ransack());
        BaseMod.addCard(new SleightOfHand());
        BaseMod.addCard(new SharpPractice());
        BaseMod.addCard(new IllGottenGains());
        BaseMod.addCard(new Pickpocket());
        BaseMod.addCard(new Gut());
        BaseMod.addCard(new QuickThinking());
        BaseMod.addCard(new Patience());
        BaseMod.addCard(new ShadowCloak());
        BaseMod.addCard(new HideInTheShadows());
        BaseMod.addCard(new Sidestep());
        BaseMod.addCard(new StickyFingers());
        BaseMod.addCard(new CloakAndDagger());
        BaseMod.addCard(new CripplingStrike());
        BaseMod.addCard(new Lie());
        BaseMod.addCard(new SwiftSlash());


        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // UnlockTracker.unlockCard(GuiltTrip.ID);


        UnlockTracker.unlockCard(FeignTrip.ID);
        UnlockTracker.unlockCard(ThousandBallBearings.ID);
        UnlockTracker.unlockCard(LieInWait.ID);
        UnlockTracker.unlockCard(Multitask.ID);
        UnlockTracker.unlockCard(Gaslight.ID);
        UnlockTracker.unlockCard(Reobtain.ID);
        UnlockTracker.unlockCard(SteakOut.ID);
        UnlockTracker.unlockCard(ShadowReserves.ID);
        UnlockTracker.unlockCard(PaydayLoan.ID);
        UnlockTracker.unlockCard(HeartBreaker.ID);
        UnlockTracker.unlockCard(PerfectDagger.ID);
        UnlockTracker.unlockCard(ShadowCalamity.ID);
        UnlockTracker.unlockCard(Ambush.ID);
        UnlockTracker.unlockCard(Mercy.ID);
        UnlockTracker.unlockCard(StrikeThief.ID);
        UnlockTracker.unlockCard(DefendThief.ID);
        UnlockTracker.unlockCard(SimilarSkills.ID);
        UnlockTracker.unlockCard(SwiftTread.ID);
        UnlockTracker.unlockCard(ViciousAssault.ID);
        UnlockTracker.unlockCard(DirtyDeedsDDC.ID);
        UnlockTracker.unlockCard(AttackOfOpportunity.ID);
        UnlockTracker.unlockCard(Gut.ID);
        UnlockTracker.unlockCard(Darts.ID);
        UnlockTracker.unlockCard(DoubleDealing.ID);
        UnlockTracker.unlockCard(Prep.ID);
        UnlockTracker.unlockCard(ShadowCloak.ID);
        UnlockTracker.unlockCard(HideInTheShadows.ID);
        UnlockTracker.unlockCard(Sidestep.ID);
        UnlockTracker.unlockCard(StickyFingers.ID);
        UnlockTracker.unlockCard(CloakAndDagger.ID);
        UnlockTracker.unlockCard(CripplingStrike.ID);
        UnlockTracker.unlockCard(ShadowEvade.ID);
        UnlockTracker.unlockCard(QuickThinking.ID);
        UnlockTracker.unlockCard(Patience.ID);
        UnlockTracker.unlockCard(OneStepAhead.ID);
        UnlockTracker.unlockCard(FollowUp.ID);
        UnlockTracker.unlockCard(Exploit.ID);
        UnlockTracker.unlockCard(BeautifulLies.ID);
        UnlockTracker.unlockCard(Murder.ID);
        UnlockTracker.unlockCard(DramaticFeign.ID);
        UnlockTracker.unlockCard(ShadowForm.ID);
        UnlockTracker.unlockCard(Stab.ID);
        UnlockTracker.unlockCard(CloakAndDaggerThief.ID);
        UnlockTracker.unlockCard(Ransack.ID);
        UnlockTracker.unlockCard(SleightOfHand.ID);
        UnlockTracker.unlockCard(SharpPractice.ID);
        UnlockTracker.unlockCard(IllGottenGains.ID);
        UnlockTracker.unlockCard(Pickpocket.ID);
        UnlockTracker.unlockCard(Lie.ID);
        UnlockTracker.unlockCard(SwiftSlash.ID);

        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin Editing Strings");


        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "thiefmodAssets/localization/ThiefMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "thiefmodAssets/localization/ThiefMod-Power-Strings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "thiefmodAssets/localization/ThiefMod-Relic-Strings.json");

        logger.info("Done Edtting Strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        final String[] elusive = {"shadowstep", "Shadowstep"};
        BaseMod.addKeyword(elusive, "Become elusive, reducing incoming damage by 10% per stack. " +
                "NL If you use a Backstab card immediately after " +
                "NL a Shadowstep card, it gains it's backstab effect.");

        final String[] steal = {"steal", "Steal, stolen, Stolen"};
        BaseMod.addKeyword(steal, "Stolen cards are mostly low energy card with Exhaust." +
                "NL They range from specific effects from any class, to cards unique for the Thief." +
                "NL They are added to your hand unless stated otherwise.");

        final String[] backstab = {"BackstabPower", "backstab"};
        BaseMod.addKeyword(backstab, "If this is the first card you play in a turn, it changes its effect.");
    }
    // ================ /LOAD THE KEYWORDS/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "theThief:" + idText;
    }

}
