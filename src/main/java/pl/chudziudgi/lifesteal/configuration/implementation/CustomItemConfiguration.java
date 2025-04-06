package pl.chudziudgi.lifesteal.configuration.implementation;

import eu.okaeri.configs.OkaeriConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;

import java.util.List;

public class CustomItemConfiguration extends OkaeriConfig {

    public ItemStack barrier = new ItemBuilder(Material.TURTLE_SCUTE)
            .setName("&6&l❁ &fᴢᴀᴍᴋɴɪęᴄɪᴇ ᴡ sᴏʙɪᴇ")
            .setLore(
                    "&d&lprzedmiot eventowy",
                    "",
                    "&fᴏᴘɪs",
                    "&7ᴢᴀᴍʏᴋᴀ ᴜᴅᴇʀᴢᴇɴɪᴀ ᴘʀᴢᴇᴄɪᴡɴɪᴋᴀ ᴡ ᴋʟᴀᴛᴄę ɴᴀ 10 sᴇᴋᴜɴᴅ"
            )
            .build();

}
