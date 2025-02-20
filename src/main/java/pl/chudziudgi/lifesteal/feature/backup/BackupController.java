package pl.chudziudgi.lifesteal.feature.backup;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.feature.pet.PetUtil;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.time.Instant;
import java.util.ArrayList;

public class BackupController implements Listener {

    private final UserService userService;

    public BackupController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        User user = this.userService.findUserByNickName(player.getName());

        if (user.getBackups().size() >= 21) {
            user.getBackups().removeLast();
        }

        ItemStack[] inventory = player.getInventory().getContents();
        ArrayList<ItemStack> itemList = new ArrayList<>();

        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                itemList.add(itemStack);
            }
        }

        user.getPetDataArrayList().forEach(pet -> {
            itemList.add(PetUtil.createItemStackPet(pet.getPetData()));
        });

        if (itemList.isEmpty()) return;

        user.getBackups().add(new Backup(
                Instant.now(),
                player.getLevel(),
                player.getExp(),
                ItemStackSerializable.write(itemList)
        ));
    }
}
