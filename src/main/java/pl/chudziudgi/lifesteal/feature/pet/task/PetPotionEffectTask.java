package pl.chudziudgi.lifesteal.feature.pet.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PetPotionEffectTask extends BukkitRunnable {

    private static final int TICK_DELAY = 20 * 5;

    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public PetPotionEffectTask(UserService userService, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, TICK_DELAY, TICK_DELAY);
    }

    @Override
    public void run() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            User user = userService.findUserByUUID(player.getUniqueId());
            if (user == null || user.getPetDataArrayList().isEmpty()) {
                continue;
            }

            List<PotionEffect> effects = user.getPetDataArrayList().stream()
                    .map(pet -> {
                        PotionEffectType type = PotionEffectType.getById(pet.getPetData().getPotionEffect());
                        return type != null ? new PotionEffect(type, TICK_DELAY, 0, true, false) : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!effects.isEmpty()) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.addPotionEffects(effects);
                    }
                }.runTask(this.survivalPlugin);
            }
        }
    }
}