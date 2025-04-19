package pl.chudziudgi.lifesteal.feature.lootcase;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.LootCaseConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.List;

@Command(name = "case")
@Permission("cebula.case.command")
public class LootCaseCommand {

    private final LootCaseConfiguration lootCaseConfiguration;
    private final LootCaseHandler lootCaseHandler;

    public LootCaseCommand(LootCaseConfiguration lootCaseConfiguration, LootCaseHandler lootCaseHandler) {
        this.lootCaseConfiguration = lootCaseConfiguration;
        this.lootCaseHandler = lootCaseHandler;
    }

    @Execute(name = "set-location")
    void setCase(@Context Player player, @Arg("skrzynia") LootCase lootCase) {
        Block targetBlock = player.getTargetBlock(null, 6);
        if (targetBlock.getType() != Material.AIR) {
            lootCase.setLocation(targetBlock.getLocation());
            this.lootCaseHandler.reloadLootCaseHolograms();
            this.lootCaseConfiguration.save();
            MessageUtil.sendMessage(player, "&aNowa lokalizacja dla case %s".formatted(lootCase.getName()));
        } else {
            MessageUtil.sendMessage(player, "&cNie znaleziono bloku do ustawienia lokalizacji case");
        }
    }

    @Execute(name = "create")
    void create(@Context Player player, @Arg("skrzynia") String s) {
        Block targetBlock = player.getTargetBlock(null, 6);
        if (targetBlock.getType() != Material.AIR) {
            this.lootCaseConfiguration.lootCases.add(new LootCase(s, "", targetBlock.getLocation(), "", List.of()));
            this.lootCaseConfiguration.save();
            MessageUtil.sendMessage(player, "&aStworzono nową skrzynie: " + s);
        }
    }

    @Execute(name = "remove")
    void remove(@Context Player player, @Arg("skrzynia") LootCase lootCase) {
        this.lootCaseConfiguration.lootCases.remove(lootCase);
        MessageUtil.sendMessage(player, "&aUsunięto skrzynie.");
    }


    @Execute(name = "klucz")
    void execute(@Context CommandSender sender, @Arg Player player, @Arg LootCase lootCase, @Arg int i) {
        ItemStack itemStack = ItemStackSerializable.readItemStack(lootCase.getKeyItemStack());
        if (itemStack == null) return;
        itemStack.setAmount(i);
        player.getInventory().addItem(itemStack);
    }

    @Execute(name = "kluczeall")
    void execute(@Context Player player, @Arg LootCase lootCase, @Arg("ilość") Integer i) {
        if (i == null || i <= 0) {
            MessageUtil.sendMessage(player, "Podaj poprawną liczbę kluczy większą od 0.");
            return;
        }
        ItemStack itemStack = ItemStackSerializable.readItemStack(lootCase.getKeyItemStack());
        if (itemStack == null) return;
        itemStack.setAmount(i);
        Bukkit.getOnlinePlayers().forEach(player1 -> player1.getInventory().addItem(itemStack));
        MessageUtil.sendMessage(player, "Dodano " + i + " kluczy dla skrzyni: " + lootCase.getName() + " wszystkim graczom.");
    }

    @Permission("cebula.case.additem.admin")
    @Execute(name = "additem")
    void execute(@Context Player sender, @Arg("skrzynia") LootCase lootCase, @Arg("szansa") double chance) {
        if (sender.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            MessageUtil.sendMessage(sender, "&cMusisz coś trzymać w łapce");
            return;
        }
        lootCase.getDropItems().add(new LootCaseChance(ItemStackSerializable.write(sender.getInventory().getItemInMainHand()), chance));
        this.lootCaseConfiguration.save();
        MessageUtil.sendMessage(sender, "&aDodanp item do " + lootCase.getName());
    }

    @Permission("cebula.case.removeitem.admin")
    @Execute(name = "removeitem")
    void executeRemoveItem(@Context Player sender, @Arg("skrzynia") LootCase lootCase, @Arg int i) {
        int index = i - 1;
        if (lootCase.getDropItems().isEmpty()) {
            MessageUtil.sendMessage(sender, "&cLista przedmiotów w tej skrzynce jest pusta.");
            return;
        }

        if (index < 0 || index >= lootCase.getDropItems().size()) {
            MessageUtil.sendMessage(sender, "&cNieprawidłowy indeks. Lista zawiera " + lootCase.getDropItems().size() + " element(y/ów).");
            return;
        }

        lootCase.getDropItems().remove(index);
        this.lootCaseConfiguration.save();
        MessageUtil.sendMessage(sender, "&aUsunięto przedmiot z pozycji " + index + " z listy dropów skrzynki: " + lootCase.getName());
    }

    @Permission("cebula.case.additem.admin")
    @Execute(name = "setkey")
    void execute(@Context Player sender, @Arg("skrzynia") LootCase lootCase) {
        lootCase.setKeyItemStack(ItemStackSerializable.write(sender.getInventory().getItemInMainHand()));
        this.lootCaseConfiguration.save();
        MessageUtil.sendMessage(sender, "&aUstawiono klucz dla: " + lootCase.getName());
    }
}
