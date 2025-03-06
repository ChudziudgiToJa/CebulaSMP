package pl.chudziudgi.lifesteal.feature.enderchest;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
public class EnderChest implements Serializable {
    private String name;
    private Map<Integer, String> inventory;
    private UUID uuid;

    public EnderChest(String name, Map<Integer, String> inventory) {
        this.name = name;
        this.inventory = inventory;
        this.uuid = UUID.randomUUID();
    }

    public Map<Integer, String> getItemStackSerializableMap() {
        return inventory;
    }

    public void setItemStackSerializableMap(Map<Integer, String> inventory) {
        this.inventory = inventory;
    }
}
