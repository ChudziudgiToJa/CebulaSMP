package pl.chudziudgi.lifesteal.feature.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CheckService {

    private ArrayList<UUID> playersCheckUuid = new ArrayList<>();

    public void add(UUID uuid) {
        playersCheckUuid.add(uuid);
    }

    public void remove(UUID uuid) {
        playersCheckUuid.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return playersCheckUuid.contains(uuid);
    }

}
