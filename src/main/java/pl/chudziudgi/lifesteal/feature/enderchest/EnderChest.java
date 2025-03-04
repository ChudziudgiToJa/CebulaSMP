package pl.chudziudgi.lifesteal.feature.enderchest;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;


@Getter
@Setter
public class EnderChest {

    private String name;
    private ArrayList<String> itemStackSerializableToStringArrayList;
    private UUID uuid;

    public EnderChest(String name, ArrayList<String> itemStackSerializableToStringArrayList) {
        this.name = name;
        this.itemStackSerializableToStringArrayList = itemStackSerializableToStringArrayList;
        this.uuid = UUID.randomUUID();
    }
}
