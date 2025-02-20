package pl.chudziudgi.lifesteal.feature.clan.service;

import pl.chudziudgi.lifesteal.database.UpdateType;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.repository.ClanRepository;

import java.util.Collection;
import java.util.Map;
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
        for (Clan clan : clanConcurrentHashMap.values()) {
            if (clan.getOwnerName().equals(nickName)) {
                return clan;
            }

            for (String member : clan.getMemberArrayList()) {
                if (member.equals(nickName)) {
                    return clan;
                }
            }
        }
        return null;
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
                .filter(clan -> clan.getOwnerName().contains(nickName))
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
}

