package pl.chudziudgi.lifesteal.feature.pet.task;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.List;
import java.util.Objects;

public class PetPotionEffectTask extends BukkitRunnable {

    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public PetPotionEffectTask(UserService userService, SurvivalPlugin survivalPlugin) {
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
        this.runTaskTimerAsynchronously(this.survivalPlugin, 20, 0);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null || user.getPetDataArrayList().isEmpty()) return;
            List<PotionEffect> effects = user.getPetDataArrayList().stream()
                    .map(pet -> PotionEffectType.getById(pet.getPetData().getPotionEffect()))
                    .filter(Objects::nonNull)
                    .map(effectType -> new PotionEffect(effectType, 40, 0, true, false))
                    .toList();

            if (!effects.isEmpty()) {
                Bukkit.getScheduler().runTask(this.survivalPlugin, () -> {
                    player.addPotionEffects(effects);
                });
            }
        });
    }

}
