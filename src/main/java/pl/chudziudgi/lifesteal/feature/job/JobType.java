package pl.chudziudgi.lifesteal.feature.job;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public enum JobType implements Serializable {
    KILLER("Zabójca", List.of(
            "&7Z tą pracą otrzymasz monety za zabójstwa mobów jak i graczy.",
            "&7Im silniejszy przeciwnik, tym większa nagroda!",
            "&7Doskonal swoje umiejętności i zarabiaj na walkach."
    )),
    MINER("Górnik", List.of(
            "&7Pracując jako górnik, otrzymasz monety za wydobywanie surowców.",
            "&7Im cenniejszy minerał, tym większa szansa na nagrodę!",
            "&7Eksploruj podziemia i odkrywaj bogactwa ukryte pod ziemią."
    )),
    LUMBERJACK("Drwal", List.of(
            "&7Zarabiaj monety za ścinanie drzew i zbieranie drewna.",
            "&7Im więcej drzew zetniesz, tym więcej zarobisz!",
            "&7Pomóż rozwijać gospodarkę drewna w swoim świecie."
    )),
    FISHER("Rybak", List.of(
            "&7Otrzymuj monety za łowienie ryb w różnych akwenach.",
            "&7Rzadkie gatunki ryb są warte więcej monet!",
            "&7Zostań mistrzem wędkowania i odkryj sekrety wodnych głębin."
    )),
    CLEAR("Bezrobotny", List.of(""));

    private final String polishName;

    private final List<String> descriptionStringList;

    JobType(String polishName, List<String> descriptionStringList) {
        this.polishName = polishName;
        this.descriptionStringList = descriptionStringList;
    }
}
