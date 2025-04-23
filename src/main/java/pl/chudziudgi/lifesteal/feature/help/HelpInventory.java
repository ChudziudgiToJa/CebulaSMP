package pl.chudziudgi.lifesteal.feature.help;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.chudziudgi.lifesteal.SurvivalPlugin;
import pl.chudziudgi.lifesteal.util.ItemBuilder;
import pl.chudziudgi.lifesteal.util.MessageUtil;
import pl.chudziudgi.lifesteal.util.SimpleInventory;

import java.util.Arrays;

public class HelpInventory {

    private final SurvivalPlugin survivalPlugin;

    public HelpInventory(SurvivalPlugin survivalPlugin) {
        this.survivalPlugin = survivalPlugin;
    }

    public void show(final Player player) {
        SimpleInventory simpleInventory = new SimpleInventory(this.survivalPlugin, 54, MessageUtil.smallText("&6&lPOMOC"));
        Inventory inventory = simpleInventory.getInventory();


        Integer[] glassBlueSlots = new Integer[]{
                1, 3, 5, 7, 9, 17, 27, 35, 47, 51, 2, 4, 6, 18, 26, 36, 44, 46, 48, 50, 52, 0, 8, 45, 53, 49
        };

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot,
                new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build()));


        ItemBuilder helpItem = new ItemBuilder(Material.PAPER, 1)
                .setName(" ")
                .setLore("&2&lKOMENDY POMOC",
                        "",
                        "&8• &e/sklep &8- &fotwiera sklepy",
                        "&8• &e/market &8- &fotwiera market",
                        "&8• &e/spawn &8- &fteleport na spawn",
                        "&8• &e/kit &8- &fmenu zestawow",
                        "&8• &e/warp &8- &fotwiera menu",
                        "&8• &e/ec &8- &fotwiera enderchest",
                        "&8• &e/craftingi &8- &fotwiera schematy craftingów",
                        "&8• &e/kosz &8- &fotwiera kosz",
                        "&8• &e/vip /mvip /cebulak &8- &fDostepne rangi na serwerze",
                        "&8• &e/helpop (wiadomosc) &8- &fWysyla wiadomosc do administracji",
                        "&8• &e/msg (nick) (wiadomosc) &8- &fWysyla prywatna wiadomosc do gracza",
                        "&8• &e/r (wiadomosc) &8- &fOdpowiada na ostatnia prywatna wiadomosc",
                        "&8• &e/itemshop &8- &fOtwiera ItemShop serwera",
                        "&8• &e/sethome /home &8- &fsystem domów",
                        "&8• &e/pety &8- &fsystem petów dające efekty",
                        "&8• &e/praca &8- &fmożliwość zarobku",
                        "&8• &e/disco &8- &fdisco zbroja (kosmetyk)",
                        "");

        inventory.setItem(13, helpItem.build());

        ItemBuilder vipItem = new ItemBuilder(Material.GOLDEN_CHESTPLATE, 1)
                .setName(" ")
                .setLore("&fꑅ",
                        "",
                        "&8• &fUprawnienia:",
                        "&8• &acodzienne vpln &8- &fdrop od 0.05 do 0.10",
                        "&8• &adrop w afk zone &8- &f20%",
                        "&8• &a/kit vip &8- &fDostep do kit vip",
                        "&8• &a/feed &8- &fDostep do feed",
                        "&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe",
                        "&8• &a/ec &8- &fDostep do enderchesta",
                        "&8• &a/workbench &8- &fDostep do craftingu",
                        "&8• &fspawner nie wymaga posiadania silk touch",
                        "&8• &f10% więcej szansy na drop spawnera",
                        "&8• &f10% więcej szansy na drop głowy z gracza",
                        "&8",
                        "&8• &fmozliwosc ustawienia 2 domów",
                        "&8• &fUnikalny prefix na czacie i tabliscie",
                        "&8",
                        "&8• &fRangę zakupisz pod &e/itemshop",
                        "");

        inventory.setItem(30, vipItem.build());

        ItemBuilder svipItem = new ItemBuilder(Material.IRON_CHESTPLATE, 1)
                .setName(" ")
                .setLore("&fꑇ",
                        "",
                        "&8• &fUprawnienia:",
                        "&8• &acodzienne vpln &8- &fdrop od 0.05 do 0.30",
                        "&8• &adrop w afk zone &8- &f30%",
                        "&8• &a/kit mvip &8- &fDostep do kit mvip",
                        "&8• &a/kit vip &8- &fDostep do kit vip",
                        "&8• &a/feed &8- &fDostep do feed",
                        "&8• &a/ec &8- &fDostep do enderchesta",
                        "&8• &a/workbench &8- &fDostep do craftingu",
                        "&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe",
                        "&8• &a/sethome &8- &fDostep do ustawienia domu",
                        "&8• &a/kowal &8- &fDostep do przenośnego kowala",
                        "&8• &a/maga &8- &fDostep do przenośnego maga",
                        "&8• &fspawner nie wymaga posiadania silk touch",
                        "&8• &f20% więcej szansy na drop spawnera",
                        "&8• &f20% więcej szansy na drop głowy z gracza",
                        "&8",
                        "&8• &fauto fly na spawn",
                        "&8• &fMozliwosc pisania na kolorowo",
                        "&8• &fUnikalny prefix na czacie i tabliscie",
                        "&8• &fmozliwosc ustawienia 4 domów",
                        "&8",
                        "&8• &fRangę zakupisz pod &e/itemshop",
                        "");

        inventory.setItem(31, svipItem.build());

        ItemBuilder sponsorItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1)
                .setName(" ")
                .setLore("&fꑍ",
                        "",
                        "&8• &fUprawnienia:",
                        "&8• &acodzienne vpln &8- &fdrop od 0.05 do 0.60",
                        "&8• &adrop w afk zone &8- &f40%",
                        "&8• &a/kit cebulak &8- &fDostep do kit cebulak",
                        "&8• &a/kit mvip &8- &fDostep do kit mvip",
                        "&8• &a/kit vip &8- &fDostep do kit vip",
                        "&8• &a/feed &8- &fDostep do feed",
                        "&8• &a/ec &8- &fDostep do enderchesta",
                        "&8• &a/workbench &8- &fDostep do craftingu",
                        "&8• &a/hat &8- &fMozliwosc zalozenia itemku na glowe",
                        "&8• &a/sethome &8- &fDostep do ustawienia domu",
                        "&8• &a/kowal &8- &fDostep do przenośnego kowala",
                        "&8• &a/maga &8- &fDostep do przenośnego maga",
                        "&8• &fspawner nie wymaga posiadania silk touch",
                        "&8• &f40% więcej szansy na drop spawnera",
                        "&8• &f40% więcej szansy na drop głowy z gracza",
                        "&8",
                        "&8• &fauto fly na spawn",
                        "&8• &fMozliwosc pisania na kolorowo",
                        "&8• &fUnikalny prefix na czacie i tabliscie",
                        "&8• &fmozliwosc ustawienia 6 domów",
                        "&8",
                        "&8• &fRangę zakupisz pod &e/itemshop",
                        "");

        inventory.setItem(32, sponsorItem.build());

        inventory.setItem(49, new ItemBuilder(Material.BARRIER)
                .setName("&czamknij")
                .build());

        simpleInventory.click(event -> {
            event.setCancelled(true);
            if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

            if (event.getSlot() == 49) {
                player.closeInventory();
                player.playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE, 5, 5);
            }

        });

        player.openInventory(inventory);
    }
}