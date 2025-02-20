package pl.chudziudgi.lifesteal.feature.vanish;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.pet.PetHologramHandler;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class VanishHandler {

    public void toggleVanish(Player player, User user, SurvivalPlugin survivalPlugin) {
        user.setVanish(!user.isVanish());
        Player target = Bukkit.getPlayer(user.getNickName());
        if (target == null) {
            return;
        }

        if (user.isVanish()) {
            target.setMetadata("vanished", new FixedMetadataValue(survivalPlugin, true));
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                if (!onlinePlayer.hasPermission("cebulasmp.vanish.see")) {
                    onlinePlayer.hidePlayer(survivalPlugin, target); // Dodano plugin jako argument
                }
            });
            user.getPetDataArrayList().forEach(pet -> {
                DHAPI.removeHologram(pet.getUuid().toString());
            });
            MessageUtil.sendMessage(player, "&b&lV &fjest &aaktywny");
        } else {
            target.removeMetadata("vanished", survivalPlugin);
            Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
                onlinePlayer.showPlayer(survivalPlugin, target); // Dodano plugin jako argument
            });
            user.getPetDataArrayList().forEach(pet -> {
                PetHologramHandler.create(target, user, pet);
            });
            player.playSound(player.getLocation(), Sound.ENTITY_BAT_LOOP, 10, 10);
            MessageUtil.sendMessage(player, "&b&lV &fzostał &cwyłączony");
        }
    }
}
