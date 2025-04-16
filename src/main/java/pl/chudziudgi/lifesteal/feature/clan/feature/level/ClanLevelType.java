package pl.chudziudgi.lifesteal.feature.clan.feature.level;

import lombok.Getter;

@Getter
public enum ClanLevelType {
    LITTLE("malutka", 2, 15, 0, 2, 1),
    SMALL("mała", 5, 30, 50000, 5, 3),
    NORMAL("normalna", 10, 50, 120000, 10, 5),
    BIG("duza", 15, 70, 260000, 20, 10),
    HEAVY("wielka", 20, 100, 500000, 30, 15);

    private final String polishName;
    private final int maxMember;
    private final int size;
    private final int price;
    private final int maxHopper;
    private final int maxSpawner;


    ClanLevelType(String polishName, int maxMember, int size, int price, int maxHopper, int maxSpawner) {
        this.polishName = polishName;
        this.maxMember = maxMember;
        this.size = size;
        this.price = price;
        this.maxHopper = maxHopper;
        this.maxSpawner = maxSpawner;
    }
}
