package pl.chudziudgi.lifesteal.feature.clan;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.database.repository.Identifiable;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.ClanCuboidHearthLocation;
import pl.chudziudgi.lifesteal.feature.clan.feature.level.ClanLevelType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
public class Clan implements Serializable, Identifiable<String> {

    private String uuid;
    private String tag;
    private ClanMember owner;

    private ClanCuboidHearthLocation location;
    private ClanCuboidHearthLocation teleportLocation;
    private List<ClanMember> members = new ArrayList<>();

    private ClanLevelType clanLevelType = ClanLevelType.LITTLE;
    private boolean pvp = false;

    public Clan(Player player, String tag) {
        this.uuid = player.getUniqueId().toString();
        this.owner = new ClanMember(player);
        this.tag = tag.toUpperCase();

        this.location = new ClanCuboidHearthLocation(player.getX(), player.getY(), player.getZ());
        this.teleportLocation = location;
        this.members.add(owner);
    }

    @Override
    public String getId() {
        return uuid;
    }

    public Location getBukkitLocation() {
        return new Location(
                Bukkit.getWorlds().getFirst(),
                location.getX(),
                location.getY(),
                location.getZ()
        );
    }

    public Location getBukkitTeleportLocation() {
        return new Location(
                Bukkit.getWorlds().getFirst(),
                teleportLocation.getX(),
                teleportLocation.getY(),
                teleportLocation.getZ()
        );
    }

    public boolean containsMemberByUUID(String uuid) {
        return members.stream().anyMatch(member -> member.getUuid().toString().equals(uuid));
    }

    public boolean containsMemberByName(String name) {
        return members.stream().anyMatch(member -> member.getName().equalsIgnoreCase(name));
    }

    public Optional<ClanMember> getMember(String name) {
        return members.stream().filter(member -> member.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<ClanMember> getMember(UUID uuid) {
        return members.stream().filter(member -> member.getUuid().equals(uuid)).findFirst();
    }

    public boolean isOwner(UUID uuid) {
        return owner.getUuid().equals(uuid);
    }

    public boolean isOwner(String name) {
        return owner.getName().equalsIgnoreCase(name);
    }

}
