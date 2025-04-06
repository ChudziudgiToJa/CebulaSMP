package pl.chudziudgi.lifesteal.feature.customitem.border;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CustomItemBorderManager {

    public static void sendWorldBorderPacket(Player player, Location center, double size) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.INITIALIZE_BORDER);
        packet.getDoubles().write(0, center.getX());
        packet.getDoubles().write(1, center.getZ());
        packet.getDoubles().write(2, size);
        packet.getDoubles().write(3, size);
        packet.getLongs().write(0, 0L);
        packet.getIntegers().write(0, 29999984);
        packet.getIntegers().write(1, 15);
        packet.getIntegers().write(2, 5);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }

    public static void resetWorldBorder(Player player) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.INITIALIZE_BORDER);
        Location worldSpawn = player.getWorld().getSpawnLocation();
        packet.getDoubles().write(0, worldSpawn.getX());
        packet.getDoubles().write(1, worldSpawn.getZ());
        packet.getDoubles().write(2, 60000000.0);
        packet.getDoubles().write(3, 60000000.0);
        packet.getLongs().write(0, 0L);
        packet.getIntegers().write(0, 29999984);
        packet.getIntegers().write(1, 15);
        packet.getIntegers().write(2, 5);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
    }

}
