package pl.chudziudgi.lifesteal.feature.pet;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.feature.pet.object.Pet;
import pl.chudziudgi.lifesteal.util.ItemBuilder;

public class PetHologramHandler {

    public static void create(Player player, final Pet pet) {
        Hologram oldHolo = DHAPI.getHologram(pet.getUuid().toString());

        if (oldHolo != null) {
            DHAPI.removeHologram(pet.getUuid().toString());
        }

        Hologram hologram = DHAPI.createHologram(
                pet.getUuid().toString(),
                player.getLocation(),
                false
        );

        DHAPI.addHologramLine(hologram, new ItemBuilder(Material.PLAYER_HEAD)
                .setHeadOwner(pet.getPetData().getSkinValue())
                .build());


    }
}

