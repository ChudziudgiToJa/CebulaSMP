package pl.chudziudgi.lifesteal.feature.pet;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.configuration.implementation.PetConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

@Command(name = "pet")
public class PetCommand {

    private final PetConfiguration pluginConfiguration;
    private final PetInventory petInventory;
    private final UserService userService;
    private final SurvivalPlugin survivalPlugin;

    public PetCommand(PetConfiguration pluginConfiguration, PetInventory petInventory, UserService userService, SurvivalPlugin survivalPlugin) {
        this.pluginConfiguration = pluginConfiguration;
        this.petInventory = petInventory;
        this.userService = userService;
        this.survivalPlugin = survivalPlugin;
    }


    @Execute
    public void openUserInventory(@Context Player player) {
        User user = this.userService.findUserByUUID(player.getUniqueId());
        if (user == null) return;
        if (user.getPetDataArrayList().isEmpty()) {
            MessageUtil.sendTitle(player, "&c", "&cnie masz petów", 20,50,20);
            return;
        }
        this.petInventory.showPetInventory(this.survivalPlugin,player, user);
    }

    @Execute
    @Permission("gencash.pet.command.admin.other")
    public void openOtherUserInventory(@Context Player player, @Arg User user) {
        this.petInventory.showPetInventory(this.survivalPlugin,player, user);
    }

    @Execute(name = "admin")
    @Permission("gencash.pet.command.admin")
    public void openAllPets(@Context Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin,9 * 3, "&3&lpety");
        Inventory inventory = simpleInventory.getInventory();

        this.pluginConfiguration.petDataDataList.forEach(pet -> {
            inventory.addItem(PetUtil.createItemStackPet(pet)
            );
        });

        player.openInventory(inventory);
    }
}
