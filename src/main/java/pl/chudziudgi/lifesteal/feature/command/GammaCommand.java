package pl.chudziudgi.lifesteal.feature.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "gamma", aliases = "noktowizja")
public class GammaCommand {

    @Execute
    public void execute(@Context Player player) {
        PotionEffectType nightVision = PotionEffectType.NIGHT_VISION;

        if (player.hasPotionEffect(nightVision)) {
            player.removePotionEffect(nightVision);
            MessageUtil.sendMessage(player, "&cwyłączono gamme");
        } else {
            PotionEffect effect = new PotionEffect(nightVision, Integer.MAX_VALUE, 0, false, false);
            player.addPotionEffect(effect);
            MessageUtil.sendMessage(player, "&awłączono gamme");
        }
    }
}
