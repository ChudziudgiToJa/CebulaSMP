package pl.chudziudgi.lifesteal.feature.customitem;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.CustomItemConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomItemController implements Listener {
    private final CustomItemConfiguration config;
    private final Map<UUID, Map<CustomItemType, Long>> cooldowns = new HashMap<>();
    private final SurvivalPlugin plugin;

    public CustomItemController(SurvivalPlugin plugin, CustomItemConfiguration config) {
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler
    public void onClickCustomItem(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null) return;

        CustomItem customItem = getCustomItemFromStack(item);
        if (customItem == null) return;

        if (isOnCooldown(player, customItem)) {
            player.sendMessage(ChatColor.RED + "Przedmiot jest na cooldownie!");
            return;
        }

        setCooldown(player, customItem);
        executeItemAbility(player, customItem);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (!(event.getEntity() instanceof Player target)) return;

        ItemStack item = attacker.getInventory().getItemInMainHand();
        CustomItem customItem = getCustomItemFromStack(item);
        if (customItem == null) return;

        if (isOnCooldown(attacker, customItem)) {
            attacker.sendMessage(ChatColor.RED + "Przedmiot jest na cooldownie!");
            return;
        }

        setCooldown(attacker, customItem);
        executeItemAbility(attacker, customItem);
    }

    private void executeItemAbility(Player player, CustomItem customItem) {
        switch (customItem.getCustomItemType()) {
            case BOW_SWAP -> handleBowSwap(player);
            case GOBLIN_STICK -> handleGoblinStick(player);
            case EGG_SWORD -> handleEggSword(player);
            case UNLUCKY_CHICK -> handleUnluckyChick(player);
            case ENDLESS_FIREWORK -> handleEndlessFirework(player);
            default -> player.sendMessage(ChatColor.RED + "Brak przypisanej umiejętności do tego przedmiotu!");
        }
    }

    private void handleBowSwap(Player player) {
        player.launchProjectile(Arrow.class);
    }

    private void handleBowSwapEffect(Player attacker, Player target) {
        if (Math.random() < 0.5) {
            Location attackerLoc = attacker.getLocation();
            Location targetLoc = target.getLocation();
            attacker.teleport(targetLoc);
            target.teleport(attackerLoc);
            attacker.sendMessage(ChatColor.GREEN + "Zamiana miejsc udana!");
            target.sendMessage(ChatColor.RED + "Zostałeś zamieniony miejscami!");
        }
    }

    private void handleGoblinStick(Player player) {
        Player target = getTargetedPlayer(player);
        if (target != null) {
            Inventory inv = target.getInventory();
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack i : inv.getContents()) {
                if (i != null) items.add(i);
            }
            Collections.shuffle(items);
            inv.clear();
            for (ItemStack i : items) {
                inv.addItem(i);
            }
            player.sendMessage(ChatColor.GREEN + "Pomieszałeś ekwipunek " + target.getName() + "!");
            target.sendMessage(ChatColor.RED + "Twój ekwipunek został pomieszany!");
        }
    }

    private void handleEggSword(Player player) {
        Player target = getTargetedPlayer(player);
        if (target != null) {
            createEggSchematic(player.getLocation(), 15);
            player.sendMessage(ChatColor.GREEN + "Stworzono schemat jajka!");
        }
    }

    private void handleUnluckyChick(Player player) {
        Location loc = player.getLocation();
        int rabbitCount = ThreadLocalRandom.current().nextInt(2, 5);

        for (int i = 0; i < rabbitCount; i++) {
            Rabbit rabbit = (Rabbit) loc.getWorld().spawnEntity(loc, EntityType.RABBIT);
            rabbit.setTarget(getNearestPlayer(rabbit, player));
            rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
        }
    }

    private void handleEndlessFirework(Player player) {
        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK_ROCKET);
        FireworkMeta meta = fw.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder()
                .withColor(Color.fromRGB(255, 0, 0))
                .withFade(Color.fromRGB(0, 255, 0))
                .with(FireworkEffect.Type.BALL_LARGE)
                .trail(true)
                .flicker(true)
                .build());
        fw.setFireworkMeta(meta);
    }

    private void createEggSchematic(Location center, int durationSeconds) {
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Location loc = center.clone().add(x, y, z);
                    if (loc.distance(center) <= 2) {
                        Block block = loc.getBlock();
                        Material original = block.getType();
                        block.setType(y > 0 ? Material.WHITE_WOOL : Material.YELLOW_WOOL);

                        Bukkit.getScheduler().runTaskLater(plugin, () ->
                                block.setType(original), durationSeconds * 20L);
                    }
                }
            }
        }
    }

    private Player getNearestPlayer(Entity entity, Player exclude) {
        return entity.getWorld().getPlayers().stream()
                .filter(p -> !p.equals(exclude))
                .min(Comparator.comparingDouble(p -> p.getLocation().distance(entity.getLocation())))
                .orElse(null);
    }

    private Player getTargetedPlayer(Player player) {
        return player.getNearbyEntities(5, 5, 5)
                .stream()
                .filter(e -> e instanceof Player)
                .map(e -> (Player) e)
                .filter(p -> !p.equals(player))
                .findFirst()
                .orElse(null);
    }

    private CustomItem getCustomItemFromStack(ItemStack stack) {
        return config.customItems.stream()
                .filter(item -> ItemStackSerializable.readItemStack(item.getItemStack()) != null)
                .findFirst()
                .orElse(null);
    }

    private boolean isOnCooldown(Player player, CustomItem item) {
        Map<CustomItemType, Long> playerCooldowns = cooldowns.getOrDefault(player.getUniqueId(), new HashMap<>());
        Long lastUse = playerCooldowns.get(item.getCustomItemType());
        if (lastUse == null) return false;

        long currentTime = System.currentTimeMillis();
        long cooldownEnd = lastUse + (item.getCoolDownTime() * 1000L);
        return currentTime < cooldownEnd;
    }

    private void setCooldown(Player player, CustomItem item) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(item.getCustomItemType(), System.currentTimeMillis());
    }
}
