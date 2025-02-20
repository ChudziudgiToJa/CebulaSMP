package pl.chudziudgi.lifesteal.feature.clan.repository;

import pl.chudziudgi.lifesteal.database.DatabaseRepository;
import pl.chudziudgi.lifesteal.feature.clan.Clan;

public class ClanRepository extends DatabaseRepository<String, Clan> {

    public ClanRepository() {
        super(Clan.class, "uuid", "clans");
    }

}
