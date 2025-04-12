package pl.chudziudgi.lifesteal.feature.clan.feature.level;

import lombok.Getter;

@Getter
public enum ClanLevelType {
    LITTLE("malutka", 2, 15),
    SMALL("mała", 5, 30),
    NORMAL("normalna", 10, 50),
    BIG("duza", 15, 70),
    HEAVY("wielka", 20, 100);

    private final String polishName;
    private final int maxMember;
    private final int size;

    ClanLevelType(String polishName, int maxMember, int size) {
        this.polishName = polishName;
        this.maxMember = maxMember;
        this.size = size;
    }
}
