package pl.chudziudgi.lifesteal.feature.protection;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "ochrona")
public class ProtectionCommand {

    private final ProtectionManager protectionManager;

    public ProtectionCommand(ProtectionManager protectionManager) {
        this.protectionManager = protectionManager;
    }

    @Execute
    public void execute(@Context Player player) {
        if (this.protectionManager.isProtection(player.getUniqueId())) {
            long remaining = this.protectionManager.getUuidLongHashMap().get(player.getUniqueId()) - System.currentTimeMillis();
            MessageUtil.sendMessage(player, "&aPosiadasz jeszcze: %s ochrony".formatted(
                    DurationUtil.getTimeFormatCombat(remaining)
            ));
        } else {
            MessageUtil.sendMessage(player, "&cNie posiadasz ochrony.");
        }
    }

    @Execute(name = "add")
    @Permission("cebulasmp.command.ochrona.admin")
    public void addProtection(@Context Player player, @Arg Player target, @Arg int i) {
        if (target == null) {
            MessageUtil.sendMessage(player, "&cGracz nie jest online!");
            return;
        }

        long time = i * 1000L;

        this.protectionManager.add(target.getUniqueId(), time);
        MessageUtil.sendMessage(player, "&aDodano %s ochrony graczowi %s".formatted(
                DurationUtil.getTimeFormatCombat(time),
                target.getName()
        ));
    }

    @Execute(name = "clear")
    @Permission("cebulasmp.command.ochrona.admin")
    public void clearProtection(@Context Player player, @Arg Player target) {
        if (target == null) {
            MessageUtil.sendMessage(player, "&cGracz nie jest online!");
            return;
        }

        if (!this.protectionManager.isProtection(target.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cTen gracz nie ma aktywnej ochrony!");
            return;
        }

        this.protectionManager.getUuidLongHashMap().remove(target.getUniqueId());
        MessageUtil.sendMessage(player, "&aWyczyściłeś ochronę graczowi %s".formatted(target.getName()));
    }

    @Execute(name = "wyłącz")
    public void disableProtection(@Context Player player) {
        if (!this.protectionManager.isProtection(player.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cTen gracz nie ma aktywnej ochrony!");
            return;
        }

        this.protectionManager.getUuidLongHashMap().remove(player.getUniqueId());
        MessageUtil.sendMessage(player, "&aWyłączyłeś ochronę");
    }
}