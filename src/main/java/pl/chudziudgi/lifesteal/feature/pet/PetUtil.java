package pl.chudziudgi.lifesteal.feature.pet;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.PetConfiguration;
import pl.chudziudgi.lifesteal.feature.pet.object.PetData;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class PetUtil {

    public static PetData getPetFromItem(final PetConfiguration petconfiguration, String name) {
        return petconfiguration.petDataDataList.stream()
                .filter(petData -> name.equals(MessageUtil.smallTextToColor(petData.getName())))
                .findFirst()
                .orElse(null);
    }


    public static ItemStack createItemStackPet(final PetData petData) {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setHeadOwner(petData.getSkinValue())
                .setName(petData.getName())
                .addLore("")
                .addLore(String.valueOf(petData.getStringArrayList()))
                .addLore("")
                .addLore("&akliknij aby założyć.")
                .build();
    }
}
