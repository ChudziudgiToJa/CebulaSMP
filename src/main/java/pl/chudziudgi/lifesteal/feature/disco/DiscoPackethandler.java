package pl.chudziudgi.lifesteal.feature.disco;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class DiscoPackethandler {

    public static void sendArmorPacket(final Player player, final Player target, final Color color) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(color);
        helmet.setItemMeta(helmetMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, helmet));

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(color);
        chestplate.setItemMeta(chestplateMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, chestplate));

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(color);
        leggings.setItemMeta(leggingsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, leggings));

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(color);
        boots.setItemMeta(bootsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, boots));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

    public static void sendBootsPacket(final Player player, final Player target, final Color color) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(color);
        boots.setItemMeta(bootsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, boots));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

    public static void sendLeggingsPacket(final Player player, final Player target, final Color color) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(color);
        leggings.setItemMeta(leggingsMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, leggings));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

    public static void sendChestPlatePacket(final Player player, final Player target, final Color color) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(color);
        chestplate.setItemMeta(chestplateMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, chestplate));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

    public static void sendHelmetPacket(final Player player, final Player target, final Color color) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(color);
        helmet.setItemMeta(helmetMeta);
        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, helmet));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }


    public static void refreshArmorPacket(final Player player, final Player target) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
        packet.getIntegers().write(0, player.getEntityId());

        List<Pair<EnumWrappers.ItemSlot, ItemStack>> list = new ArrayList<>();

        list.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, player.getInventory().getHelmet()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, player.getInventory().getChestplate()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, player.getInventory().getLeggings()));
        list.add(new Pair<>(EnumWrappers.ItemSlot.FEET, player.getInventory().getBoots()));

        packet.getSlotStackPairLists().write(0, list);
        protocolManager.sendServerPacket(target, packet);
    }

}
