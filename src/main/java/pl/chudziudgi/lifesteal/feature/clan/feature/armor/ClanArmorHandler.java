package pl.chudziudgi.lifesteal.feature.clan.feature.armor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


import java.util.ArrayList;
import java.util.List;

public class ClanArmorHandler {

    public static void sendArmorPacket(final Player player, final Player target) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.GREEN);
        helmetMeta.addEnchant(Enchantment.UNBREAKING, 10, true);
        helmet.setItemMeta(helmetMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, helmet));

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.GREEN);
        chestplateMeta.addEnchant(Enchantment.UNBREAKING, 10, true);
        chestplate.setItemMeta(chestplateMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, chestplate));

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.GREEN);
        leggingsMeta.addEnchant(Enchantment.UNBREAKING, 10, true);
        leggings.setItemMeta(leggingsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, leggings));

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.GREEN);
        bootsMeta.addEnchant(Enchantment.UNBREAKING, 10, true);
        boots.setItemMeta(bootsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, boots));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

    public static void refreshArmorPacket(final Player player, final Player target) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, target.getInventory().getHelmet()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, target.getInventory().getChestplate()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, target.getInventory().getLeggings()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, target.getInventory().getBoots()));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

}
