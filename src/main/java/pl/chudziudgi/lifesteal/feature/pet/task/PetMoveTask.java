package pl.chudziudgi.lifesteal.feature.pet.task;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.feature.pet.object.Pet;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

public class PetMoveTask extends BukkitRunnable {

    private final UserService userService;

    public PetMoveTask(SurvivalPlugin survivalPlugin, UserService userService) {
        this.userService = userService;
        this.runTaskTimerAsynchronously(survivalPlugin, 0, 2);
    }


    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.findUserByUUID(player.getUniqueId());
            if (user == null) return;
            if (user.getPetDataArrayList().isEmpty()) return;

            Location playerLocation = player.getLocation();
            Vector direction = playerLocation.getDirection();

            direction.setY(0);
            direction.normalize();

            double distanceFromPlayer = 0.7;
            double heightAbovePlayer = 3.0;
            int petCount = user.getPetDataArrayList().size();
            double sideOffset = 0.6;

            for (int i = 0; i < petCount; i++) {
                Pet pet = user.getPetDataArrayList().get(i);

                Vector perpendicularDirection = direction.clone().rotateAroundY(Math.PI / 2);
                Vector hologramOffset = new Vector();

                if (petCount == 1) {
                    hologramOffset = direction.clone().multiply(-distanceFromPlayer).setY(heightAbovePlayer);
                } else if (petCount == 2) {
                    hologramOffset = perpendicularDirection.clone().multiply(i == 0 ? -sideOffset : sideOffset)
                            .add(direction.clone().multiply(-distanceFromPlayer))
                            .setY(heightAbovePlayer);
                }

                Location hologramLocation = playerLocation.clone().add(hologramOffset);
                DHAPI.moveHologram(pet.getUuid().toString(), hologramLocation);
            }

        });
    }


}
