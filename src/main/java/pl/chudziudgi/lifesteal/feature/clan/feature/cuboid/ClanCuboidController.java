package pl.chudziudgi.lifesteal.feature.clan.feature.cuboid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.*;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

public class ClanCuboidController implements Listener {

    private final ClanService clanService;

    public ClanCuboidController(ClanService clanService) {
        this.clanService = clanService;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        Location blockLocation = event.getClickedBlock().getLocation();
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.containsMemberByUUID(player.getUniqueId().toString())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;

        MessageUtil.sendActionbar(player, "&cNie możesz tego robić na terenie obcego klanu!");
        event.setCancelled(true);
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.containsMemberByUUID(player.getUniqueId().toString())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz stawiać bloków na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlock().getLocation();
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.containsMemberByUUID(player.getUniqueId().toString())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz niszczyć bloków na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlockClicked().getLocation().add(event.getBlockFace().getDirection());
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.containsMemberByUUID(player.getUniqueId().toString())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz wylewać wody na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        Location blockLocation = event.getBlockClicked().getLocation();
        if (!blockLocation.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Clan clan = this.clanService.findClanByLocation(blockLocation);
        if (clan == null) return;
        if (clan.containsMemberByUUID(player.getUniqueId().toString())) return;
        if (player.hasPermission("cebulasmp.clan.admin")) return;
        MessageUtil.sendActionbar(player, "&cNie możesz czerpać wody na terenie obcego klanu!");
        event.setCancelled(true);
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        Block block = event.getBlock();
        Block toBlock = event.getToBlock();
        if (!toBlock.getWorld().equals(Bukkit.getWorlds().getFirst())) return;
        if (!block.getWorld().equals(Bukkit.getWorlds().getFirst())) return;

        Location toLocation = toBlock.getLocation();
        Clan fromClan = this.clanService.findClanByLocation(block.getLocation());
        Clan toClan = this.clanService.findClanByLocation(toLocation);

        if (fromClan != null && toClan != null && !fromClan.equals(toClan)) {
            event.setCancelled(true);
            return;
        }

        if (toClan != null && fromClan == null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        Block piston = event.getBlock();
        Clan fromClan = this.clanService.findClanByLocation(piston.getLocation());

        for (Block block : event.getBlocks()) {
            Location toLocation = block.getLocation().add(event.getDirection().getDirection());
            Clan toClan = this.clanService.findClanByLocation(toLocation);

            if (fromClan != null && toClan != null && !fromClan.equals(toClan)) {
                event.setCancelled(true);
                return;
            }

            if (fromClan != null && toClan == null) {
                event.setCancelled(true);
                return;
            }

            if (fromClan == null && toClan != null) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        if (!event.isSticky()) return;

        Block piston = event.getBlock();
        Clan fromClan = this.clanService.findClanByLocation(piston.getLocation());

        Block movedBlock = event.getBlock();
        Location movedBlockLocation = movedBlock.getLocation();
        Clan toClan = this.clanService.findClanByLocation(movedBlockLocation);

        if (fromClan != null && toClan != null && !fromClan.equals(toClan)) {
            event.setCancelled(true);
            return;
        }

        if (fromClan != null && toClan == null) {
            event.setCancelled(true);
        }

        if (fromClan == null && toClan != null) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;
        Entity entity = event.getEntity();
        if (!(entity instanceof TNTPrimed) && !(entity instanceof Creeper)) {
            return;
        }
        for (Block block : event.blockList()) {
            if (this.clanService.isLocationOnClanCuboid(block.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }

}
