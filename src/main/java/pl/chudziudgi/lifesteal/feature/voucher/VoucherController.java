package pl.chudziudgi.lifesteal.feature.voucher;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.lifesteal.configuration.implementation.VoucherConfiguration;
import pl.chudziudgi.lifesteal.util.ItemStackSerializable;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class VoucherController implements Listener {
    private final VoucherConfiguration voucherConfiguration;

    public VoucherController(VoucherConfiguration voucherConfiguration) {
        this.voucherConfiguration = voucherConfiguration;
    }

    @EventHandler
    public void onClickPet(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.PAPER || item.getItemMeta() == null) {
            return;
        }

        for (Voucher voucher : this.voucherConfiguration.voucherArrayList) {
            ItemStack voucherItemStack = ItemStackSerializable.readItemStack(voucher.getItemStackString());
            if (voucherItemStack == null) continue;
            if (!item.equals(voucherItemStack)) continue;

            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().removeItem(item);
            }
            MessageUtil.sendTitle(player, "&a", "&aPomyślnie odebrano voucher.", 20, 60, 20);
            String command = voucher.getCommand().replace("{PLAYER}", player.getName());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            break;
        }
    }
}
