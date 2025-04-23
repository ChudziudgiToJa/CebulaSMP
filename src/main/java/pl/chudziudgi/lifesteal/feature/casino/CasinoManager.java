package pl.chudziudgi.lifesteal.feature.casino;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
public class CasinoManager {
    private final Random random;
    private final UserService userService;
    private final Map<Material, Double> multipliers;
    private final Map<Material, Integer> chances;
    private final Material[] spinningItems;

    public CasinoManager(UserService userService, Random random) {
        this.userService = userService;
        this.random = random;

        this.multipliers = new HashMap<>() {{
            put(Material.LAPIS_LAZULI, 0.2);
            put(Material.COPPER_INGOT, 0.8);
            put(Material.EMERALD, 1.5);
            put(Material.GOLD_INGOT, 2.0);
            put(Material.DIAMOND, 2.5);
            put(Material.NETHERITE_INGOT, 3.0);
            put(Material.HONEY_BLOCK, 7.0);
        }};

        this.chances = new HashMap<>() {{
            put(Material.LAPIS_LAZULI, 25);
            put(Material.COPPER_INGOT, 20);
            put(Material.EMERALD, 18);
            put(Material.GOLD_INGOT, 15);
            put(Material.DIAMOND, 10);
            put(Material.NETHERITE_INGOT, 7);
            put(Material.HONEY_BLOCK, 5);
        }};

        this.spinningItems = new Material[] {
                Material.LAPIS_LAZULI,
                Material.COPPER_INGOT,
                Material.EMERALD,
                Material.GOLD_INGOT,
                Material.DIAMOND,
                Material.NETHERITE_INGOT,
                Material.HONEY_BLOCK
        };
    }

    public Material getRandomItem() {
        int roll = random.nextInt(100) + 1;
        int cumulativeChance = 0;

        for (Map.Entry<Material, Integer> entry : chances.entrySet()) {
            cumulativeChance += entry.getValue();
            if (roll <= cumulativeChance) {
                return entry.getKey();
            }
        }

        return Material.LAPIS_LAZULI;
    }

    public double calculateMultiplier(Material[] results) {
        if (results[0] == results[1] && results[1] == results[2]) {
            return multipliers.getOrDefault(results[0], 0.0);
        }
        return 0.0;
    }

    public boolean canAfford(Player player, int bet) {
        User user = userService.findUserByUUID(player.getUniqueId());
        return user != null && user.getMoney() >= bet;
    }

    public void processBet(Player player, int bet) {
        User user = userService.findUserByUUID(player.getUniqueId());
        if (user != null) {
            user.removeMoney(bet);
        }
    }

    public void addWinnings(Player player, int amount) {
        User user = userService.findUserByUUID(player.getUniqueId());
        if (user != null) {
            user.addMoney(amount);
        }
    }

}