package pl.chudziudgi.lifesteal.feature.lifesteal;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class LifeStealController implements Listener {

    private final PluginConfiguration pluginConfiguration;

    public LifeStealController(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        Player player = event.getEntity();
        AttributeInstance victimAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (victimAttribute != null) {
            AtomicDouble victimHearts = new AtomicDouble(victimAttribute.getBaseValue());
            double newHealth = victimHearts.get() - 2;

            if (newHealth <= 1) {
                this.pluginConfiguration.lifeStealSettings.commandList.forEach(string -> {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("{PLAYER}", player.getName()));
                });
                return;
            }

            victimAttribute.setBaseValue(newHealth);
            player.getWorld().dropItemNaturally(player.getLocation(), this.pluginConfiguration.lifeStealSettings.heartItemStack);
        }
    }

    @EventHandler
    public void onClickItem(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        if (!item.isSimilar(this.pluginConfiguration.lifeStealSettings.heartItemStack)) {
            return;
        }

        AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttribute == null) {
            return;
        }
        double currentMaxHealth = maxHealthAttribute.getBaseValue();
        if (currentMaxHealth >= 80.0) {
            MessageUtil.sendTitle(player, "&c", "&cMasz już max życia.", 20, 50, 20);
            return;
        }
        double newMaxHealth = currentMaxHealth + 2;
        maxHealthAttribute.setBaseValue(newMaxHealth);
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.getInventory().removeItem(item);
        }
        MessageUtil.sendTitle(player, "&c", "&aDodano serce.", 20, 50, 20);
    }
}
