package pl.chudziudgi.lifesteal.feature.protection;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
public class ProtectionManager {

    private HashMap<UUID, Long> uuidLongHashMap = new HashMap<>();

    public boolean isProtection(UUID uuid) {
        return this.getUuidLongHashMap().containsKey(uuid) &&
                this.getUuidLongHashMap().get(uuid) > System.currentTimeMillis();
    }

    public void add(UUID uuid, Long time) {
        this.getUuidLongHashMap().put(uuid, time + System.currentTimeMillis());
    }
}
