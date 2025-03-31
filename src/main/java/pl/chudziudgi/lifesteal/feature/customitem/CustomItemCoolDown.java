package pl.chudziudgi.lifesteal.feature.customitem;

import lombok.Getter;

public class CustomItemCoolDown {
    @Getter
    private final CustomItem customItem;
    private final long endTime;

    public CustomItemCoolDown(CustomItem customItem, long endTime) {
        this.customItem = customItem;
        this.endTime = endTime;
    }

    public long getTime() {
        return endTime;
    }
}