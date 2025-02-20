package pl.chudziudgi.lifesteal.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.itemStack = is;
    }

    public ItemBuilder(Material m, int amount) {
        itemStack = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, short data) {
        itemStack = new ItemStack(m, amount, data);
    }

    public ItemBuilder(Material m, short data) {
        itemStack = new ItemStack(m, 1, data);
    }


    public ItemBuilder setLore(final String... list) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> loreList = new ArrayList<>();
        for (String text : list) {
            loreList.add(MessageUtil.smallText(text));
        }
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLore(final String... list) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();

        for (String text : list) {
            loreList.add(MessageUtil.smallText(text));
        }

        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder addEnchant(final Enchantment enchant, final int level) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchant, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemBuilder setName(final String title) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageUtil.smallText(title));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setGlow(final boolean b) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (b) {
            itemMeta.addEnchant(Enchantment.BREACH, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder hideAtributs(final boolean b) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (b) {
            assert itemMeta != null;
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setHeadOwner(final String textureUrl) {
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            return this;
        }

        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        if (meta == null) return this;

        itemStack.editMeta(SkullMeta.class, skullMeta -> {
            final UUID uuid = UUID.randomUUID();
            final PlayerProfile playerProfile = Bukkit.createProfile(uuid, uuid.toString().substring(0, 16));
            playerProfile.setProperty(new ProfileProperty("textures", textureUrl));
            skullMeta.setPlayerProfile(playerProfile);
        });
        return this;
    }
}
