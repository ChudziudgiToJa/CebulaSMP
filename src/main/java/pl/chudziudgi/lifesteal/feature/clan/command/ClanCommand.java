package pl.chudziudgi.lifesteal.feature.clan.command;

import de.rapha149.signgui.exception.SignGUIVersionException;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.chudziudgi.lifesteal.configuration.implementation.ClanConfiguration;
import pl.chudziudgi.lifesteal.feature.clan.Clan;
import pl.chudziudgi.lifesteal.feature.clan.ClanMember;
import pl.chudziudgi.lifesteal.feature.clan.feature.armor.ClanArmorHandler;
import pl.chudziudgi.lifesteal.feature.clan.feature.create.CreateSignMenu;
import pl.chudziudgi.lifesteal.feature.clan.feature.cuboid.ClanCuboidHearthLocation;
import pl.chudziudgi.lifesteal.feature.clan.feature.delete.ClanDeleteInventory;
import pl.chudziudgi.lifesteal.feature.clan.feature.invite.ClanInviteService;
import pl.chudziudgi.lifesteal.feature.clan.feature.upgrade.ClanUpgradeInventory;
import pl.chudziudgi.lifesteal.feature.clan.manager.ClanManager;
import pl.chudziudgi.lifesteal.feature.clan.service.ClanService;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.util.Optional;

@Command(name = "klan")
public class ClanCommand {

    private final UserService userService;
    private final ClanService clanService;
    private final ClanDeleteInventory clanDeleteInventory;
    private final ClanInviteService clanInviteService;
    private final CreateSignMenu createSignMenu;
    private final ClanConfiguration clanConfiguration;
    private final ClanUpgradeInventory clanUpgradeInventory;

    public ClanCommand(UserService userService, ClanService clanService, ClanDeleteInventory clanDeleteInventory, ClanInviteService clanInviteService, CreateSignMenu createSignMenu, ClanConfiguration clanConfiguration, ClanUpgradeInventory clanUpgradeInventory) {
        this.userService = userService;
        this.clanService = clanService;
        this.clanDeleteInventory = clanDeleteInventory;
        this.clanInviteService = clanInviteService;
        this.createSignMenu = createSignMenu;
        this.clanConfiguration = clanConfiguration;
        this.clanUpgradeInventory = clanUpgradeInventory;
    }


    @Execute()
    void help(@Context Player player) {
        MessageUtil.sendMessage(player, "&7Lista dostępnych komend klanowych:");
        MessageUtil.sendMessage(player, "&a/klan stwórz <tag> &7- Tworzy nowy klan z podanym tagiem. Koszt: 3500 monet.");
        MessageUtil.sendMessage(player, "&a/klan usuń &7- Usuwa twój klan (jeśli jesteś właścicielem).");
        MessageUtil.sendMessage(player, "&a/klan zaproś <gracz> &7- Zaprasza podanego gracza do twojego klanu.");
        MessageUtil.sendMessage(player, "&a/klan dołącz <tag> &7- Akceptuje zaproszenie do klanu o podanym tagu.");
        MessageUtil.sendMessage(player, "&a/klan opuść &7- Opuszcza obecny klan (jeśli nie jesteś właścicielem).");
        MessageUtil.sendMessage(player, "&a/klan wyrzuć <gracz> &7- Wyrzuca gracza z twojego klanu.");
        MessageUtil.sendMessage(player, "&a/klan pvp &7- Otwiera menu zmiany ustawień PvP klanu.");
    }


    @Execute(name = "stwórz")
    void create(@Context Player player) throws SignGUIVersionException {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan != null) {
            MessageUtil.sendMessage(player, "&cPosiadasz juz klan.");
            return;
        }

        if (player.getWorld() != Bukkit.getWorlds().getFirst()) {
            MessageUtil.sendMessage(player, "&cMusisz być w normalnym świecie aby stworzyć klanu");
            return;
        }

        if (clanService.isNearAnotherClan(player.getLocation())) {
            MessageUtil.sendMessage(player, "&cNie możesz stworzyć klanu w pobliżu innego klanu.");
            return;
        }

        User user = this.userService.findUserByUUID(player.getUniqueId());

        if (user == null) {
            return;
        }

        if (user.getMoney() < this.clanConfiguration.getClanPrice()) {
            MessageUtil.sendMessage(player, "&cNie stać cię na klan. Koszt to: " + this.clanConfiguration.getClanPrice());
            return;
        }

        this.createSignMenu.open(player, user);
    }

    @Execute(name = "usuń")
    void delete(@Context Player player) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }
        this.clanDeleteInventory.showDeleteInventory(player, clan);
    }

    @Execute(name = "dołącz")
    void acceptClanInvite(@Context Player player, @Arg String targetClan) {
        Clan playerClan = this.clanService.findClanByOwner(player.getName());
        if (playerClan != null) {
            MessageUtil.sendMessage(player, "&cPosiadasz już klan.");
            return;
        }
        Clan clan = this.clanService.findClanByTag(targetClan);
        if (clan == null) {
            MessageUtil.sendMessage(player, "&cTaki klan nie istnieje.");
            return;
        }
        if (!this.clanInviteService.clanInviteConcurrentHashMap.containsKey(clan) ||
                !this.clanInviteService.clanInviteConcurrentHashMap.get(clan).equals(player.getName())) {
            MessageUtil.sendMessage(player, "&cNie masz zaproszenia do tego klanu.");
            return;
        }

        if (clan.getMembers().size() > clan.getClanLevelType().getMaxMember()) {
            MessageUtil.sendMessage(player, "&cKlan osiągnął maksymalny limit graczy &8(%s).".formatted(clan.getClanLevelType().getMaxMember()));
            return;
        }

        clan.getMembers().add(new ClanMember(player));
        this.clanInviteService.clanInviteConcurrentHashMap.remove(clan, player.getName());
        MessageUtil.sendMessage(player, "&aPomyślnie dołączyłeś do klanu " + clan.getTag() + ".");
        clan.getMembers().forEach(s -> {
            if (!s.getUuid().equals(player.getUniqueId())) return;
            MessageUtil.sendMessage(player, "&f" + player.getName() + " &adołączył do klanu.");
        });
    }

    @Execute(name = "zaproś")
    void invinteMember(@Context Player player, @Arg Player target) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu lub nie jesteś liderem.");
            return;
        }

        if (target == null) {
            MessageUtil.sendMessage(player, "&cGracz nie jest aktywny");
            return;
        }

        Clan targetClan = this.clanService.findClanByMember(target.getName());

        if (targetClan != null) {
            MessageUtil.sendMessage(player, "&cGracz posiada już klan");
            return;
        }

        if (clan.getMembers().size() > clan.getClanLevelType().getMaxMember()) {
            MessageUtil.sendMessage(player, "&cKlan osiągnął maksymalny limit graczy &8(%s).".formatted(clan.getClanLevelType().getMaxMember()));
            return;
        }

        this.clanInviteService.inviteToClan(clan, target.getName());
        MessageUtil.sendMessage(player, "&aZaproszono do klanu: &f" + target.getName());

        Component targetMessage1 = Component.text("§aOtrzymałeś/aś zaproszenie do klanu: " + clan.getTag())
                .clickEvent(ClickEvent.runCommand("/klan dołącz " + clan.getTag()));
        target.sendMessage(targetMessage1);

        Component targetMessage2 = Component.text("      §7§npo 30s zaproszenie wygasa! §b[klik]")
                .clickEvent(ClickEvent.runCommand("/klan dołącz " + clan.getTag()));
        target.sendMessage(targetMessage2);
    }


    @Execute(name = "opuść")
    void quitClan(@Context Player player) {
        Clan clan = this.clanService.findClanByMember(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (clan.isOwner(player.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cNie możesz opuścić swojego klanu");
            return;
        }

        ClanManager.removeMember(clan, player);
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            ClanArmorHandler.refreshArmorPacket(player, player1);
            ClanArmorHandler.refreshArmorPacket(player1, player);
        });
        MessageUtil.sendMessage(player, "&aopuszczono klan: &f" + clan.getTag());
    }

    @Execute(name = "wyrzuć")
    void removeMember(@Context Player player, @Arg String clanMemberName) {
        Clan clan = this.clanService.findClanByMember(player.getUniqueId());

        if (clan == null || !clan.isOwner(player.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cNie masz klanu lub nie jesteś liderem.");
            return;
        }

        Optional<ClanMember> memberOptional = clan.getMember(clanMemberName);
        if (memberOptional.isEmpty()) {
            MessageUtil.sendMessage(player, "&4Gracz nie jest w twoim klanie.");
            return;
        }

        ClanMember member = memberOptional.get();
        if (clan.isOwner(member.getUuid())) {
            MessageUtil.sendMessage(player, "&cNie możesz wyrzucić lidera klanu.");
            return;
        }

        clan.removeMember(member);

        Player targetPlayer = Bukkit.getPlayer(member.getUuid());
        if (targetPlayer != null) {
            Bukkit.getOnlinePlayers().forEach(online -> {
                ClanArmorHandler.refreshArmorPacket(targetPlayer, online);
                ClanArmorHandler.refreshArmorPacket(online, targetPlayer);
            });
        }

        MessageUtil.sendMessage(player, "&aWyrzucono z klanu: " + member.getName());
    }


    @Execute(name = "ustaw-dom")
    void setNewHome(@Context Player player) {
        Clan clan = this.clanService.findClanByOwner(player.getName());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu lub nie jesteś liderem.");
            return;
        }
        clan.setTeleportLocation(new ClanCuboidHearthLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
        MessageUtil.sendMessage(player, "&aUstawiono nową lokalizację teleportacji.");
    }

    @Execute(name = "pvp")
    void changePvp(@Context Player player) {
        Clan clan = this.clanService.findClanByMember(player.getUniqueId());

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (!clan.isOwner(player.getUniqueId())) {
            MessageUtil.sendMessage(player, "&cNie jesteś liderem klanu.");
            return;
        }
        clan.setPvp(!clan.isPvp());
        MessageUtil.sendMessage(player, "&7Zmieniono status pvp na: " + (clan.isPvp() ? "&cwyłączony" : "&awłączony"));
    }

    @Execute(name = "info")
    void infoOther(@Context Player player, @Arg Clan clan) {
        MessageUtil.sendMessage(player, "&fklan: &a&l" + clan.getTag());
        MessageUtil.sendMessage(player, "&fzałożyciel: &a&l" + clan.getOwner().getName());
        MessageUtil.sendMessage(player, "&fLista graczy w klanie&8: &7" + ClanManager.formatPlayerStatus(clan.getMembers()));
    }

    @Execute(name = "ulepsz")
    void upgrade(@Context Player player) {
        Clan clan = this.clanService.findClanByMember(player.getUniqueId());
        User user = this.userService.findUserByUUID(player.getUniqueId());

        if (user == null) {
            return;
        }

        if (clan == null) {
            MessageUtil.sendMessage(player, "&cNie masz klanu.");
            return;
        }

        if (!clan.containsMemberByUUID(player.getUniqueId().toString())) {
            MessageUtil.sendMessage(player, "&cNie jesteś liderem klanu.");
            return;
        }

        this.clanUpgradeInventory.show(player, user, clan);
    }

    @Execute(name = "admin delete")
    @Permission("cebulasmp.command.clan.admin")
    void adminDelete(@Context Player player, @Arg Clan clan) {
        this.clanDeleteInventory.showDeleteInventory(player, clan);
    }

    @Execute(name = "admin teleport")
    @Permission("cebulasmp.command.clan.teleport")
    void clanTeleport(@Context Player player, @Arg Clan clan) {
        player.teleport(clan.getBukkitLocation());
        MessageUtil.sendMessage(player, "&aPrzeteleportowano do klanu &f" + clan.getTag());
    }
}
