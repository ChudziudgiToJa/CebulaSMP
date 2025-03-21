package pl.chudziudgi.lifesteal.feature.customitem;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustomItem implements Serializable {
    private final CustomItemType customItemType;
    private final long coolDownTime;
    private String itemStack;

    public CustomItem(CustomItemType customItemType, long coolDownTime, String itemStack) {
        this.customItemType = customItemType;
        this.coolDownTime = coolDownTime;
        this.itemStack = itemStack;
    }
}
