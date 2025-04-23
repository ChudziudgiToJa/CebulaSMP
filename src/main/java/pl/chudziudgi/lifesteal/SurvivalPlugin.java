package pl.chudziudgi.lifesteal;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.eternalcode.core.EternalCoreApi;
import com.eternalcode.core.EternalCoreApiProvider;
import com.google.gson.Gson;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import dev.rollczi.litecommands.message.LiteMessages;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.lifesteal.configuration.ConfigService;
import pl.chudziudgi.lifesteal.configuration.implementation.*;
import pl.chudziudgi.lifesteal.database.MongoDatabaseService;
import pl.chudziudgi.lifesteal.feature.abyss.AbyssTask;
import pl.chudziudgi.lifesteal.feature.afkzone.AfkZoneManager;
import pl.chudziudgi.lifesteal.feature.afkzone.AfkZoneTask;
import pl.chudziudgi.lifesteal.feature.antivoid.AntiVoidCommand;
import pl.chudziudgi.lifesteal.feature.antivoid.AntiVoidTask;
import pl.chudziudgi.lifesteal.feature.autofly.AutoFlyTask;
import pl.chudziudgi.lifesteal.feature.autorestart.AutoRestartTask;
import pl.chudziudgi.lifesteal.feature.backup.BackupCommand;
import pl.chudziudgi.lifesteal.feature.backup.BackupController;
import pl.chudziudgi.lifesteal.feature.backup.BackupInventory;
import pl.chudziudgi.lifesteal.feature.blacksmith.BlacksmithCommand;
import pl.chudziudgi.lifesteal.feature.blacksmith.BlacksmithController;
import pl.chudziudgi.lifesteal.feature.blacksmith.BlacksmithInventory;
import pl.chudziudgi.lifesteal.feature.blocker.BlockerController;
import pl.chudziudgi.lifesteal.feature.blocker.MobChunkLimitTask;
import pl.chudziudgi.lifesteal.feature.bordercollection.BorderCollectionController;
import pl.chudziudgi.lifesteal.feature.bordercollection.BorderCollectionInventory;
import pl.chudziudgi.lifesteal.feature.boss.*;
import pl.chudziudgi.lifesteal.feature.chat.ChatCharController;
import pl.chudziudgi.lifesteal.feature.check.CheckCommand;
import pl.chudziudgi.lifesteal.feature.check.CheckController;
import pl.chudziudgi.lifesteal.feature.check.CheckService;
import pl.chudziudgi.lifesteal.feature.check.CheckTask;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.ClanMember;
import pl.chudziudgi.lifesteal.feature.clan.command.ClanCommand;
import pl.chudziudgi.lifesteal.feature.clan.command.ClanCommandArgument;
import pl.chudziudgi.lifesteal.feature.clan.command.ClanMemberCommandArgument;
import pl.chudziudgi.lifesteal.feature.clan.feature.armor.ClanArmorTask;
import pl.chudziudgi.lifesteal.feature.clan.feature.create.CreatePurchaseMenu;
import pl.chudziudgi.lifesteal.feature.clan.feature.create.CreateSignMenu;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.ClanCuboidController;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.blocker.ClanCuboidCommandBlockerController;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.bossbar.ClanCuboidBossBarTak;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.combat.ClanCuboidCombatLogoutPushTask;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.portal.ClanCuboidPortal;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.particle.ClanCuboidBorderParticleTask;
import pl.chudziudgi.lifesteal.feature.clan.feature.delete.ClanDeleteInventory;
import pl.chudziudgi.lifesteal.feature.clan.feature.invite.ClanInviteService;
import pl.chudziudgi.lifesteal.feature.clan.feature.pvp.ClanPvpController;
import pl.chudziudgi.lifesteal.feature.clan.feature.upgrade.ClanUpgradeInventory;
import pl.chudziudgi.lifesteal.feature.clan.repository.ClanRepository;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.clan.task.ClanSaveTask;
import pl.chudziudgi.lifesteal.feature.combatlogout.CombatLogoutController;
import pl.chudziudgi.lifesteal.feature.combatlogout.CombatLogoutManager;
import pl.chudziudgi.lifesteal.feature.combatlogout.CombatLogoutTask;
import pl.chudziudgi.lifesteal.feature.command.*;
import pl.chudziudgi.lifesteal.feature.crafting.CraftingCommand;
import pl.chudziudgi.lifesteal.feature.crafting.CraftingInventory;
import pl.chudziudgi.lifesteal.feature.crafting.CraftingManager;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemCommand;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemCoolDownManager;
import pl.chudziudgi.lifesteal.feature.customitem.CustomItemInventory;
import pl.chudziudgi.lifesteal.feature.customitem.border.CustomItemBorderController;
import pl.chudziudgi.lifesteal.feature.dailyvpln.DailyVplnController;
import pl.chudziudgi.lifesteal.feature.dailyvpln.DailyVplnManager;
import pl.chudziudgi.lifesteal.feature.disco.DiscoCommand;
import pl.chudziudgi.lifesteal.feature.disco.DiscoInventory;
import pl.chudziudgi.lifesteal.feature.disco.DiscoTask;
import pl.chudziudgi.lifesteal.feature.economy.EconomyCommand;
import pl.chudziudgi.lifesteal.feature.economy.EconomyHolder;
import pl.chudziudgi.lifesteal.feature.economy.MoneyCommand;
import pl.chudziudgi.lifesteal.feature.economy.PayCommand;
import pl.chudziudgi.lifesteal.feature.enchanter.EnchanterCommand;
import pl.chudziudgi.lifesteal.feature.enchanter.EnchanterController;
import pl.chudziudgi.lifesteal.feature.enchanter.EnchanterInventory;
import pl.chudziudgi.lifesteal.feature.end.*;
import pl.chudziudgi.lifesteal.feature.enderchest.*;
import pl.chudziudgi.lifesteal.feature.headdrop.HeadDropController;
import pl.chudziudgi.lifesteal.feature.help.HelpCommand;
import pl.chudziudgi.lifesteal.feature.help.HelpInventory;
import pl.chudziudgi.lifesteal.feature.itemshop.ItemShopCommand;
import pl.chudziudgi.lifesteal.feature.itemshop.ItemShopInventory;
import pl.chudziudgi.lifesteal.feature.itemshop.ItemShopManager;
import pl.chudziudgi.lifesteal.feature.job.JobCommand;
import pl.chudziudgi.lifesteal.feature.job.JobController;
import pl.chudziudgi.lifesteal.feature.job.JobInventory;
import pl.chudziudgi.lifesteal.feature.killcounter.KillCounterController;
import pl.chudziudgi.lifesteal.feature.kit.KitCommand;
import pl.chudziudgi.lifesteal.feature.kit.KitInventory;
import pl.chudziudgi.lifesteal.feature.lifesteal.LifeStealCommand;
import pl.chudziudgi.lifesteal.feature.lifesteal.LifeStealController;
import pl.chudziudgi.lifesteal.feature.lootcase.*;
import pl.chudziudgi.lifesteal.feature.nether.*;
import pl.chudziudgi.lifesteal.feature.pet.PetCommand;
import pl.chudziudgi.lifesteal.feature.pet.PetController;
import pl.chudziudgi.lifesteal.feature.pet.PetInventory;
import pl.chudziudgi.lifesteal.feature.pet.task.PetMoveTask;
import pl.chudziudgi.lifesteal.feature.pet.task.PetPotionEffectTask;
import pl.chudziudgi.lifesteal.feature.pet.task.PetRemoveBuggyPetsTask;
import pl.chudziudgi.lifesteal.feature.rabatecode.RabateCodeCommand;
import pl.chudziudgi.lifesteal.feature.randomteleport.RandomTeleportCommand;
import pl.chudziudgi.lifesteal.feature.randomteleport.RandomTeleportController;
import pl.chudziudgi.lifesteal.feature.shop.ShopCommand;
import pl.chudziudgi.lifesteal.feature.shop.ShopInventory;
import pl.chudziudgi.lifesteal.feature.shop.npc.controller.NpcShopController;
import pl.chudziudgi.lifesteal.feature.shop.npc.inventory.NpcShopInventory;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShopInventory;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShopNpcController;
import pl.chudziudgi.lifesteal.feature.shop.time.TimeShopTask;
import pl.chudziudgi.lifesteal.feature.spawner.SpawnerController;
import pl.chudziudgi.lifesteal.feature.statistic.StatisticCommand;
import pl.chudziudgi.lifesteal.feature.statistic.StatisticController;
import pl.chudziudgi.lifesteal.feature.statistic.StatisticInventory;
import pl.chudziudgi.lifesteal.feature.top.TopManager;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.feature.user.command.UserCommand;
import pl.chudziudgi.lifesteal.feature.user.command.UserCommandArgument;
import pl.chudziudgi.lifesteal.feature.user.controller.JoinQuitListener;
import pl.chudziudgi.lifesteal.feature.user.repository.UserRepository;
import pl.chudziudgi.lifesteal.feature.user.task.SpentTimeTask;
import pl.chudziudgi.lifesteal.feature.user.task.UsersSaveTask;
import pl.chudziudgi.lifesteal.feature.vanish.VanishCommand;
import pl.chudziudgi.lifesteal.feature.vanish.VanishController;
import pl.chudziudgi.lifesteal.feature.vanish.VanishHandler;
import pl.chudziudgi.lifesteal.feature.villager.VillagerController;
import pl.chudziudgi.lifesteal.feature.voucher.VoucherCommand;
import pl.chudziudgi.lifesteal.feature.voucher.VoucherController;
import pl.chudziudgi.lifesteal.feature.voucher.VoucherInventory;
import pl.chudziudgi.lifesteal.feature.welcomer.WelcomeController;
import pl.chudziudgi.lifesteal.feature.wielkanoc.WielkanocCommand;
import pl.chudziudgi.lifesteal.feature.wielkanoc.WielkanocController;

import java.io.File;
import java.util.Random;
import java.util.stream.Stream;

@Getter
public final class SurvivalPlugin extends JavaPlugin {

    public static final Gson GSON = GsonHolder.GSON;
    @Getter
    public static SurvivalPlugin instance;
    private final MongoDatabaseService mongoDatabaseService = new MongoDatabaseService();
    private final UserRepository userRepository = new UserRepository();
    private final ClanRepository clanRepository = new ClanRepository();
    private final ItemShopManager itemShopManager = new ItemShopManager();
    private final ClanInviteService clanInviteService = new ClanInviteService(this);
    private final AfkZoneManager afkZoneManager = new AfkZoneManager();
    private final Random random = new Random();
    private final DailyVplnManager dailyVplnManager = new DailyVplnManager(this.random);
    private final VanishHandler vanishHandler = new VanishHandler();
    private final BossManager bossManager = new BossManager();
    private final CheckService checkService = new CheckService();
    private final CombatLogoutManager combatLogoutManager = new CombatLogoutManager();
    private TopManager topManager;
    private HologramManager hologramManager;
    public Economy economy;
    private EternalCoreApi eternalCoreApi;
    private PluginConfiguration pluginConfiguration;
    private ClanConfiguration clanConfiguration;
    private LootCaseConfiguration lootCaseConfiguration;
    private KitConfiguration kitConfiguration;
    private ItemShopConfiguration itemShopConfiguration;
    private NpcShopConfiguration npcShopConfiguration;
    private CraftingConfiguration craftingConfiguration;
    private CustomItemConfiguration customItemConfiguration;
    private CombatLogoutConfiguration combatLogoutConfiguration;
    private WorldsSettings worldsSettings;
    private PetConfiguration petconfiguration;
    private VoucherConfiguration voucherConfiguration;
    private BorderCollectionConfiguration borderCollectionConfiguration;
    private UserService userService;
    private ClanService clanService;
    private ProtocolManager protocolManager;
    private NetherManager netherManager;
    private EndManager endManager;
    private LiteCommands<CommandSender> liteCommands;

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.userService = new UserService(this.userRepository);
        this.clanService = new ClanService(this.clanRepository);

        Bukkit.getServicesManager().register(Economy.class, new EconomyHolder(this.userService), this, ServicePriority.Highest);

        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            this.getLogger().severe("Nie wykryto pluginu Valut");
            this.getServer().shutdown();
            return;
        }
        this.economy = rsp.getProvider();
    }


    @Override
    public void onEnable() {
        this.getServer().createWorld(new WorldCreator("spawn"));
        this.getServer().createWorld(new WorldCreator("arena"));

        Server server = getServer();
        instance = this;

        //load placeholderApi
        this.eternalCoreApi = EternalCoreApiProvider.provide();

        //Load fancyHolograms
        this.hologramManager = FancyHologramsPlugin.get().getHologramManager();

        //load configs files
        ConfigService configService = new ConfigService();
        File dataFolder = this.getDataFolder();
        this.pluginConfiguration = configService.create(PluginConfiguration.class, new File(dataFolder, "config.yml"));
        this.lootCaseConfiguration = configService.create(LootCaseConfiguration.class, new File(dataFolder, "lootcase.yml"));
        this.kitConfiguration = configService.create(KitConfiguration.class, new File(dataFolder, "kit.yml"));
        this.npcShopConfiguration = configService.create(NpcShopConfiguration.class, new File(dataFolder, "npcShop.yml"));
        this.itemShopConfiguration = configService.create(ItemShopConfiguration.class, new File(dataFolder, "itemshop.yml"));
        this.craftingConfiguration = configService.create(CraftingConfiguration.class, new File(dataFolder, "crafting.yml"));
        this.petconfiguration = configService.create(PetConfiguration.class, new File(dataFolder, "pet.yml"));
        this.clanConfiguration = configService.create(ClanConfiguration.class, new File(dataFolder, "klan.yml"));
        this.worldsSettings = configService.create(WorldsSettings.class, new File(dataFolder, "nether.yml"));
        this.borderCollectionConfiguration = configService.create(BorderCollectionConfiguration.class, new File(dataFolder, "border.yml"));
        this.voucherConfiguration = configService.create(VoucherConfiguration.class, new File(dataFolder, "voucher.yml"));
        this.customItemConfiguration = configService.create(CustomItemConfiguration.class, new File(dataFolder, "customitem.yml"));
        this.combatLogoutConfiguration = configService.create(CombatLogoutConfiguration.class, new File(dataFolder, "combatLog.yml"));
        // topki
        this.topManager = new TopManager(this.userService);

        new Placeholder(this.userService, this.clanService, this.worldsSettings, this.topManager).register();


        // help menu
        HelpInventory helpInventory = new HelpInventory(this);

        //TIme Shop
        TimeShopInventory timeShopInventory = new TimeShopInventory(this.userService, this.pluginConfiguration, this);

        // shop Menu
        NpcShopInventory npcShopInventory = new NpcShopInventory(this, userService, this.npcShopConfiguration);
        ShopInventory shopInventory = new ShopInventory(this, npcShopInventory, timeShopInventory);

        // job Menu
        JobInventory jobInventory = new JobInventory(this, this.userService, this.pluginConfiguration);

        // kit
        KitInventory kitInventory = new KitInventory(this, this.kitConfiguration, this.userService);

        // backup
        BackupInventory backupInventory = new BackupInventory(this, this.userService);

        // ItemShop
        ItemShopInventory itemShopInventory = new ItemShopInventory(this, this.itemShopConfiguration, this.itemShopManager, this.userService);

        // lootCase
        LootCaseInventory lootCaseInventory = new LootCaseInventory(this);
        LootCaseHandler lootCaseHandler = new LootCaseHandler(this.lootCaseConfiguration, this.hologramManager);
        lootCaseHandler.createLootCaseHolograms();

        // Statistic
        StatisticInventory statisticInventory = new StatisticInventory(this, this.userService);

        //Clan
        ClanDeleteInventory clanDeleteInventory = new ClanDeleteInventory(this, this.clanService, this.protocolManager);
        CreatePurchaseMenu createPurchaseMenu = new CreatePurchaseMenu(this, this.clanConfiguration, this.clanService);
        CreateSignMenu createSignMenu = new CreateSignMenu(this, createPurchaseMenu);
        ClanUpgradeInventory clanUpgradeInventory = new ClanUpgradeInventory(this);

        //Custom crafting
        CraftingManager craftingManager = new CraftingManager(this.craftingConfiguration);
        craftingManager.registerCraftings();

        //Pet
        PetInventory petInventory = new PetInventory();

        //Blacksmih
        BlacksmithInventory blacksmithInventory = new BlacksmithInventory(this, this.userService);

        //Crafting
        CraftingInventory craftingInventory = new CraftingInventory(this, this.craftingConfiguration);

        //Nether
        this.netherManager = new NetherManager(this.worldsSettings);

        //End
        this.endManager = new EndManager(this.worldsSettings);

        //Voucher
        VoucherInventory voucherInventory = new VoucherInventory(this, this.voucherConfiguration);

        //Disco
        DiscoInventory discoInventory = new DiscoInventory(this);

        //EnderChest
        EnderChestSignGui enderChestSignGui = new EnderChestSignGui(this);
        EnderChestInventory enderChestInventory = new EnderChestInventory(this, enderChestSignGui);

        //CustomItem
        CustomItemInventory customItemInventory = new CustomItemInventory(this, this.customItemConfiguration);
        CustomItemCoolDownManager customItemCoolDownManager = new CustomItemCoolDownManager();

        //Enchanter
        EnchanterInventory enchanterInventory = new EnchanterInventory(this, this.userService, this.random);

        BorderCollectionInventory borderCollectionInventory = new BorderCollectionInventory(this, this.borderCollectionConfiguration, this.userService);
        Bukkit.getWorlds().getFirst().getWorldBorder().setSize(this.borderCollectionConfiguration.getWorldSize());

        //Restart
        new AutoRestartTask(this).scheduleNextRestart();

        // load data
        this.userRepository.findAll().forEach(this.userService::addUser);
        this.clanRepository.findAll().forEach(this.clanService::addClan);


        // load commands
        this.liteCommands = LiteCommandsBukkit.builder()
                .settings(settings -> settings
                        .fallbackPrefix("cebulaSMP")
                        .nativePermissions(false)
                )
                .commands(
                        new HelpCommand(this, helpInventory),
                        new TrashCommand(this),
                        new EconomyCommand(this.userService),
                        new JobCommand(jobInventory, this.pluginConfiguration),
                        new KitCommand(kitInventory, this.kitConfiguration, this.userService),
                        new BackupCommand(backupInventory, this.userService),
                        new VplnCommand(this.userService),
                        new ItemShopCommand(itemShopInventory),
                        new LootCaseCommand(this.lootCaseConfiguration, lootCaseHandler),
                        new MoneyCommand(this.userService),
                        new StatisticCommand(statisticInventory),
                        new PayCommand(this.userService),
                        new ClanCommand(this.userService, this.clanService, clanDeleteInventory, this.clanInviteService, createSignMenu, this.clanConfiguration,clanUpgradeInventory),
                        new VanishCommand(this.userService, this.vanishHandler, this),
                        new PetCommand(this.petconfiguration, petInventory, this.userService, this),
                        new CraftingCommand(craftingInventory),
                        new DiscordCommand(this.pluginConfiguration),
                        new NetherCommand(this.netherManager, this.worldsSettings),
                        new ReloadConfigurationCommand(configService),
                        new ShopCommand(shopInventory),
                        new RandomTeleportCommand(this.pluginConfiguration),
                        new LiveCommand(),
                        new UserCommand(this.userService),
                        new VoucherCommand(this.voucherConfiguration, voucherInventory),
                        new DiscoCommand(discoInventory, this.userService),
                        new EndCommand(this.endManager, this.worldsSettings),
                        new LifeStealCommand(this.pluginConfiguration),
                        new EnderChestCommand(this.userService, enderChestInventory),
                        new BossCommand(bossManager),
                        new RabateCodeCommand(this.pluginConfiguration, this.userService),
                        new CheckCommand(this.pluginConfiguration, this.checkService),
                        new CustomItemCommand(customItemInventory),
                        new GammaCommand(),
                        new AntiVoidCommand(this.pluginConfiguration),
                        new WielkanocCommand(this.pluginConfiguration),
                        new EnchanterCommand(enchanterInventory),
                        new BlacksmithCommand(blacksmithInventory)
                )
                .message(LiteMessages.MISSING_PERMISSIONS, permissions -> "&4ɴɪᴇ ᴘᴏꜱɪᴀᴅᴀꜱᴢ ᴡʏᴍᴀɢᴀɴᴇᴊ ᴘᴇʀᴍɪꜱᴊɪ&c: " + permissions.asJoinedText())
                .argument(User.class, new UserCommandArgument(this.userService))
                .argument(Clan.class, new ClanCommandArgument(this.clanService))
                .argument(LootCase.class, new LootCaseCommandArgument(this.lootCaseConfiguration))
                .argument(ClanMember.class, new ClanMemberCommandArgument(this.clanService))
                .invalidUsage(
                        new InvalidCommandHandle()
                )
                .build();
        // load Listeners
        Stream.of(
                new JoinQuitListener(this.userService),
                new NpcShopController(this.npcShopConfiguration, npcShopInventory),
                new JobController(this.userService, this.random, this.pluginConfiguration),
                new BackupController(this.userService),
                new BlockerController(this.pluginConfiguration),
                new DailyVplnController(this.userService, this.pluginConfiguration, this.dailyVplnManager),
                new LootCaseController(this.lootCaseConfiguration, lootCaseInventory),
                new StatisticController(this.userService),
                new ClanPvpController(this.clanService),
                new VanishController(this.userService, this),
                new KillCounterController(this),
                new PetController(this.userService, this.petconfiguration, this),
                new ChatCharController(),
                new BlacksmithController(blacksmithInventory, this.pluginConfiguration),
                new NetherController(this.worldsSettings, this.netherManager, this.eternalCoreApi),
                new BorderCollectionController(this.borderCollectionConfiguration, borderCollectionInventory),
                new RandomTeleportController(this.pluginConfiguration, this.eternalCoreApi),
                new VoucherController(this.voucherConfiguration),
                new EndController(this.worldsSettings, this.endManager, this.eternalCoreApi),
                new LifeStealController(this.pluginConfiguration),
                new EnderChestController(this.userService, enderChestInventory),
                new BossController(this.random, bossManager, this.userService),
                new CheckController(this.checkService, this.pluginConfiguration),
                new TimeShopNpcController(this.pluginConfiguration, timeShopInventory),
                new CustomItemBorderController(this, this.customItemConfiguration, customItemCoolDownManager),
                new ClanCuboidController(this.clanService, this.clanConfiguration),
                new ClanCuboidCommandBlockerController(this.clanService, this.clanConfiguration),
                new SpawnerController(),
                new WelcomeController(this.pluginConfiguration, this.userService),
                new HeadDropController(),
                new VillagerController(),
                new WielkanocController(this.random, this.pluginConfiguration),
                new CombatLogoutController(this.combatLogoutConfiguration, this.combatLogoutManager, this.clanService),
                new EnchanterController(this.pluginConfiguration,enchanterInventory)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));

        new UsersSaveTask(this, this.userService);
        new ClanSaveTask(this, this.clanService);
        new SpentTimeTask(this, this.userService);
        new AbyssTask(this);
        new AfkZoneTask(this, afkZoneManager, this.lootCaseConfiguration, userService);
        new ClanArmorTask(this, this.clanService);
        new PetMoveTask(this, this.userService);
        new PetPotionEffectTask(this.userService, this);
        new PetRemoveBuggyPetsTask(this, this.userService);
        new NetherTask(this, this.worldsSettings);
        new MobChunkLimitTask(this);
        new DiscoTask(this, this.random, this.clanService, this.userService);
        new EndTask(this, this.worldsSettings);
        new TimeShopTask(this, this.userService);
        new BossHealthBossBarTask(this, bossManager);
        new CheckTask(this, this.checkService);
        new AntiVoidTask(this, this.pluginConfiguration);
        new AutoFlyTask(this);
        new ClanCuboidBorderParticleTask(this.clanService, this);
        new ClanCuboidBossBarTak(this.clanService, this);
        new ClanCuboidPortal(this.clanService, this);
        new EndStatusTask(this, this.worldsSettings).scheduleDailyTasks();
        new NetherStatusTask(this, this.worldsSettings).scheduleDailyTasks();
        new CombatLogoutTask(this, this.combatLogoutManager, this.combatLogoutConfiguration);
        new ClanCuboidCombatLogoutPushTask(this,this.clanService, this.combatLogoutManager);
    }

    @Override
    public void onDisable() {
        this.protocolManager.removePacketListeners(this);
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
        this.userService.saveAllUsers();
        this.clanService.saveAllClans();
        this.bossManager.removeBoss();
    }

}
