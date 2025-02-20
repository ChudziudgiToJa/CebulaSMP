package pl.chudziudgi.lifesteal.feature.kit;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class KitData implements Serializable {
    private final String name;
    private final long time;


    public KitData(String name, long time) {
        this.name = name;
        this.time = time;
    }
}
