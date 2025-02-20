package pl.chudziudgi.lifesteal.feature.backup;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.feature.pet.PetUtil;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.time.Instant;
import java.util.ArrayList;

@Command(name = "backup")
@Permission("cebulasmp.backup.command")
public class BackupCommand {

    private final BackupInventory backupInventory;
    private final UserService userService;


    public BackupCommand(BackupInventory backupInventory, UserService userService) {
        this.backupInventory = backupInventory;
        this.userService = userService;
    }

    @Execute(name = "open")
    void openTarget(@Context Player player, @Arg Player target) {
        this.backupInventory.show(player, target);
    }

    @Execute(name = "create")
    void create(@Context Player player, @Arg Player target) {
        User user = this.userService.findUserByUUID(target.getUniqueId());

        if (user == null) {
            MessageUtil.sendMessage(player, "&cNie ma takiego gracza na serwerze.");
            return;
        }

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
