package pl.chudziudgi.lifesteal.feature.clan.service;

import org.bukkit.Location;
import pl.chudziudgi.lifesteal.database.UpdateType;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.ClanCuboidHearthLocation;
import pl.chudziudgi.lifesteal.feature.clan.repository.ClanRepository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClanService {
    private final ClanRepository clanRepository;
    private final Map<String, Clan> clanConcurrentHashMap = new ConcurrentHashMap<>();

    public ClanService(ClanRepository clanRepository) {
        this.clanRepository = clanRepository;
    }

    public void addClan(Clan clan) {
        this.clanConcurrentHashMap.put(clan.getId(), clan);
    }

    public void createClan(Clan clan) {
        this.clanConcurrentHashMap.put(clan.getUuid(), clan);
        clanRepository.update(clan, clan.getId(), UpdateType.CREATE);
    }

    public void saveClan(Clan clan) {
        clanRepository.update(clan, clan.getId(), UpdateType.UPDATE);
    }

    public void removeClan(Clan clan) {
        this.clanConcurrentHashMap.remove(clan.getId());
        clanRepository.update(clan, clan.getId(), UpdateType.REMOVE);
    }

    public Clan findClanByMember(String nickName) {
        return this.clanConcurrentHashMap.values()
                .stream()
                .filter(clan -> clan.containsMemberByName(nickName))
                .findFirst()
                .orElse(null);
    }

    public Clan findClanByMember(UUID nickName) {
        return this.clanConcurrentHashMap.values()
                .stream()
                .filter(clan -> clan.containsMemberByUUID(nickName.toString()))
                .findFirst()
                .orElse(null);
    }

    public Clan findClanByTag(String tag) {
        return this.clanConcurrentHashMap.values()
                .stream()
                .filter(clan -> clan.getTag().contains(tag.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    public Clan findClanByOwner(String nickName) {
        return this.clanConcurrentHashMap.values()
                .stream()
                .filter(clan -> clan.isOwner(nickName))
                .findFirst()
                .orElse(null);
    }

    public Clan findClanByLocation(Location playerLocation) {
        return this.clanConcurrentHashMap.values()
                .stream()
                .filter(clan -> {
                    ClanCuboidHearthLocation clanLocation = clan.getLocation();
                    double minX = clanLocation.getX() - clan.getClanLevelType().getSize();
                    double maxX = clanLocation.getX() + clan.getClanLevelType().getSize();
                    double minZ = clanLocation.getZ() - clan.getClanLevelType().getSize();
                    double maxZ = clanLocation.getZ() + clan.getClanLevelType().getSize();

                    return playerLocation.getX() >= minX && playerLocation.getX() <= maxX
                            && playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ;
                })
                .findFirst()
                .orElse(null);
    }


    public void saveAllClans() {
        this.clanConcurrentHashMap.forEach((s, clan) -> {
                    saveClan(clan);
                }
        );
    }

    public Collection<Clan> getAllClans() {
        return this.clanConcurrentHashMap.values();
    }

    public boolean isNearAnotherClan(Location playerLocation) {
        return this.clanConcurrentHashMap.values().stream().anyMatch(existingClan -> {
            ClanCuboidHearthLocation clanLocation = existingClan.getLocation();

            double distanceSquared = Math.pow(clanLocation.getX() - playerLocation.getX(), 2)
                    + Math.pow(clanLocation.getZ() - playerLocation.getZ(), 2);

            return distanceSquared <= Math.pow(200, 2);
        });
    }

    public boolean isLocationOnClanCuboid(Location blockLocation) {
        for (Clan clan : clanConcurrentHashMap.values()) {
            ClanCuboidHearthLocation clanLocation = clan.getLocation();
            double minX = clanLocation.getX() - clan.getClanLevelType().getSize();
            double maxX = clanLocation.getX() + clan.getClanLevelType().getSize() + 1;
            double minZ = clanLocation.getZ() - clan.getClanLevelType().getSize();
            double maxZ = clanLocation.getZ() + clan.getClanLevelType().getSize() + 1;

            if (blockLocation.getX() >= minX && blockLocation.getX() <= maxX
                    && blockLocation.getZ() >= minZ && blockLocation.getZ() <= maxZ) {
                return true;
            }
        }
        return false;
    }

}

