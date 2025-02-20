package pl.chudziudgi.lifesteal;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class InvalidCommandHandle implements InvalidUsageHandler<CommandSender> {

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> commandSenderInvalidUsage, ResultHandlerChain<CommandSender> resultHandlerChain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = commandSenderInvalidUsage.getSchematic();
        MessageUtil.sendMessage(sender, "&cPoprawne uzycie: " + schematic.first());
    }
}
