package pl.chudziudgi.lifesteal.feature.kit;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.KitConfiguration;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

@Command(name = "kit")
public class KitCommand {

    private final KitInventory kitInventory;
    private final KitConfiguration kitConfiguration;
    private final UserService userService;

    public KitCommand(KitInventory kitInventory, KitConfiguration kitConfiguration, UserService userService) {
        this.kitInventory = kitInventory;
        this.kitConfiguration = kitConfiguration;
        this.userService = userService;
    }

    @Execute
    void execute(@Context Player player) {
        if (!this.kitConfiguration.kitStatus) {
            MessageUtil.sendTitle(player, "", "&czestawy są aktuanie wyłączone", 20,50,20);
            return;
        }
        this.kitInventory.show(player);
    }


    @Permission("cebula.kit.command.give")
    @Execute(name = "give")
    void execute(@Context Player player, @Arg Player target, @Arg String string) {
        this.kitConfiguration.kitList.forEach(kit -> {
            if (kit.getName().equals(string)) {
                kit.getItemStackArrayList().forEach(itemStack -> {
                    target.getInventory().addItem(itemStack);
                });
                kit.getCustomItemList().forEach(s -> {
                    ItemStack itemStack = ItemStackSerializable.readItemStack(s);
                    if (itemStack == null) return;
                    target.getInventory().addItem(itemStack);
                });
                MessageUtil.sendMessage(player, "Nadano zestaw " + string + " dla " + target.getName());
            }
        });
    }

    @Permission("cebula.kit.command.give")
    @Execute(name = "cleartime")
    void execute(@Context Player player, @Arg User user, @Arg String kitname) {
        user.getKits().forEach(kitData -> {
            if (kitData.getName().equals(kitname)) {
                user.getKits().remove(kitData);
                MessageUtil.sendMessage(player, "&aUsunięto czas oczekiwania na kit " + kitname + " dla: " + user.getNickName());
            }
        });
    }

    @Permission("cebula.kit.command.customitem")
    @Execute(name = "addcustomitem")
    void execute(@Context Player sender, @Arg String s) {
        this.kitConfiguration.kitList.forEach(kit -> {
            if (kit.getName().equals(s)) {
                kit.getCustomItemList().add(ItemStackSerializable.write(sender.getInventory().getItemInMainHand()));
                MessageUtil.sendMessage(sender, "&adodano przedmiot do listy itemów kitu: "  + kit.getName());
                this.kitConfiguration.save();
            }
        });
    }

    @Permission("cebula.kit.command.customitem")
    @Execute(name = "removecustomitem")
    void executeRemove(@Context Player sender, @Arg String kitName, @Arg int i) {
        int index = i -1;
        this.kitConfiguration.kitList.forEach(kit -> {
            if (kit.getName().equals(kitName)) {

                if (kit.getCustomItemList().isEmpty()) {
                    MessageUtil.sendMessage(sender, "&cLista przedmiotów w tym kicie jest pusta.");
                    return;
                }

                if (index < 0 || index >= kit.getCustomItemList().size()) {
                    MessageUtil.sendMessage(sender, "&cNieprawidłowy indeks. Lista zawiera " + kit.getCustomItemList().size() + " element(y/ów).");
                    return;
                }

                kit.getCustomItemList().remove(index);
                MessageUtil.sendMessage(sender, "&aUsunięto przedmiot z pozycji " + index + " z listy itemów kitu: " + kit.getName());
                this.kitConfiguration.save();
            }
        });
    }


    @Permission("cebula.kit.command.toggle")
    @Execute(name = "admin toggle")
    void toggle (@Context CommandSender commandSender) {
        this.kitConfiguration.kitStatus = !this.kitConfiguration.kitStatus;
        MessageUtil.sendMessage(commandSender, "&bkity zostały: " + (this.kitConfiguration.kitStatus ? "&awłączone" : "&cwyłączone"));
        this.kitConfiguration.save();
    }
}
