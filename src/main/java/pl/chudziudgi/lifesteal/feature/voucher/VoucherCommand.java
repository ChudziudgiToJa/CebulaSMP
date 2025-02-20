package pl.chudziudgi.lifesteal.feature.voucher;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.VoucherConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.List;

@Command(name = "voucher")
@Permission("cebulasmp.voucher.admin")
public class VoucherCommand {

    private final VoucherConfiguration voucherConfiguration;
    private final VoucherInventory voucherInventory;

    public VoucherCommand(VoucherConfiguration voucherConfiguration, VoucherInventory voucherInventory) {
        this.voucherConfiguration = voucherConfiguration;
        this.voucherInventory = voucherInventory;
    }

    @Execute(name = "create")
    void create(@Context Player player, @Arg("komenda") List<String> string) {
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem.getType().equals(Material.AIR)) {
            MessageUtil.sendMessage(player, "&cMusisz trzymać przedmiot w łapce.");
            return;
        }

        this.voucherConfiguration.voucherArrayList.add(new Voucher(ItemStackSerializable.write(handItem), String.join(" ", string)));
        this.voucherConfiguration.save();
        MessageUtil.sendMessage(player, "&aStworzono voucher");
    }

    @Execute(name = "menu")
    void menu(@Context Player player) {
        this.voucherInventory.show(player);
    }

}
