package pl.chudziudgi.lifesteal.feature.lifesteal;

import com.google.common.util.concurrent.AtomicDouble;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.PluginConfiguration;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.PlayerUtil;

@Command(name = "wypłać")
public class LifeStealCommand {

    private final PluginConfiguration pluginConfiguration;

    public LifeStealCommand(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Execute
    void onCommand(@Context Player player) {
        AttributeInstance victimAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (victimAttribute == null) return;

        AtomicDouble victimHearts = new AtomicDouble(victimAttribute.getBaseValue());

        if (victimHearts.get() <= 2) {
            MessageUtil.sendTitle(player, "&c", "&cMasz za mało serc.", 20,50,20);
            return;
        }
        victimAttribute.setBaseValue(victimHearts.get() - 2);
        PlayerUtil.addItemStack(player, this.pluginConfiguration.lifeStealSettings.heartItemStack);
        MessageUtil.sendTitle(player, "&c", "&awypłacono.", 20,50,20);
    }

}
