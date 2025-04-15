package pl.chudziudgi.lifesteal.feature.clan.feature.level;

import lombok.Getter;

@Getter
public enum ClanLevelType {
    LITTLE("malutka", 2, 15, 0),
    SMALL("mała", 5, 30, 50000),
    NORMAL("normalna", 10, 50, 120000),
    BIG("duza", 15, 70, 260000),
    HEAVY("wielka", 20, 100, 500000);

    private final String polishName;
    private final int maxMember;
    private final int size;
    private final int price;

    ClanLevelType(String polishName, int maxMember, int size, int price) {
        this.polishName = polishName;
        this.maxMember = maxMember;
        this.size = size;
        this.price = price;
    }
}
