package pl.chudziudgi.lifesteal.feature.job;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class JobController implements Listener {

    private static final Set<Material> ore = Set.of(
            Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE,
            Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE,
            Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE,
            Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE,
            Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE,
            Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE,
            Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE,
            Material.REDSTONE_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.ANCIENT_DEBRIS, Material.GILDED_BLACKSTONE,Material.STONE,Material.DEEPSLATE
    );
    private static final Set<Material> log = Set.of(
            Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
            Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG,
            Material.MANGROVE_LOG, Material.CHERRY_LOG,
            Material.CRIMSON_STEM, Material.WARPED_STEM,
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_MANGROVE_LOG, Material.STRIPPED_CHERRY_LOG,
            Material.STRIPPED_CRIMSON_STEM, Material.STRIPPED_WARPED_STEM
    );
    private final UserService userService;
    private final Random random;
    private final PluginConfiguration pluginConfiguration;

    public JobController(UserService userService, Random random, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.random = random;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        User user = this.userService.findUserByNickName(killer.getName());
        if (user.getJobType() == JobType.KILLER) {
            if (random.nextDouble() < 0.20) {
                user.addMoney(2);
                MessageUtil.sendTitle(killer, "", "&2+&a2 monet", 20, 50, 20);
            }
            if (random.nextDouble() < 0.01) {
                JobDropChance jobDropChance = JobChanceManager.pickRandomItem(this.pluginConfiguration.jobSettings.jobItems.get(JobType.KILLER));

                HashMap<Integer, ItemStack> leftover = killer.getInventory().addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(jobDropChance.getItemStackString())));
                leftover.values().forEach(remaining ->
                        killer.getWorld().dropItemNaturally(killer.getLocation(), remaining)
                );
            }
        }
    }

    @EventHandler
    public void onBlockBreakOre(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());
        if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }
        if (user.getJobType() == JobType.MINER) {
            if (ore.contains(blockType)) {
                if (random.nextDouble() < 0.05) {
                    user.addMoney(2);
                    MessageUtil.sendTitle(player, "", "&2+&a2 monety", 20, 50, 20);
                }
                if (random.nextDouble() < 0.01) {
                    JobDropChance jobDropChance = JobChanceManager.pickRandomItem(this.pluginConfiguration.jobSettings.jobItems.get(JobType.MINER));

                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(jobDropChance.getItemStackString())));
                    leftover.values().forEach(remaining ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                    );
                    leftover.clear();
                    event.getBlock().getDrops().clear();
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreakLog(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        User user = this.userService.findUserByNickName(player.getName());
        if (user.getJobType() == JobType.LUMBERJACK) {
            if (log.contains(blockType)) {
                if (random.nextDouble() < 0.05) {
                    user.addMoney(3);
                    MessageUtil.sendTitle(player, "", "&2+&a3 monet", 20, 50, 20);
                }
                if (random.nextDouble() < 0.01) {
                    JobDropChance jobDropChance = JobChanceManager.pickRandomItem(this.pluginConfiguration.jobSettings.jobItems.get(JobType.LUMBERJACK));

                    HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(jobDropChance.getItemStackString())));
                    leftover.values().forEach(remaining ->
                            player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                    );
                    event.getBlock().getDrops().clear();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.isCancelled() || event.getState() != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());
        if (user.getJobType() == JobType.FISHER) {
            if (random.nextDouble() < 0.10) {
                user.addMoney(20);
                MessageUtil.sendTitle(player, "", "&2+&a20 monet", 20, 50, 20);
            }
            if (random.nextDouble() < 0.01) {
                JobDropChance jobDropChance = JobChanceManager.pickRandomItem(this.pluginConfiguration.jobSettings.jobItems.get(JobType.FISHER));

                HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(Objects.requireNonNull(ItemStackSerializable.readItemStack(jobDropChance.getItemStackString())));
                leftover.values().forEach(remaining ->
                        player.getWorld().dropItemNaturally(player.getLocation(), remaining)
                );
            }
        }
    }
}