package pl.chudziudgi.lifesteal.feature.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;

import java.util.List;

public class EconomyHolder implements Economy {
    private final UserService userService;

    public EconomyHolder(UserService userService) {
        this.userService = userService;
    }
    /* In use */

    @Override
    public boolean isEnabled() {
        return true;
    }

    /* Balance */

    @Override
    public double getBalance(String p) {
        User user = this.userService.findUserByNickName(p);
        return user.getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer p) {
        User user = this.userService.findUserByNickName(p.getName());
        return user.getMoney();
    }

    @Override
    public double getBalance(String p, String arg1) {
        User user = this.userService.findUserByNickName(p);
        return user.getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer p, String arg1) {
        User user = this.userService.findUserByNickName(p.getName());
        return user.getMoney();
    }

    /* Deposit on player account */

    @Override
    public EconomyResponse depositPlayer(String p, double a) {
        User user = this.userService.findUserByNickName(p);
        user.addMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, double a) {
        User user = this.userService.findUserByNickName(p.getName());
        user.addMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String p, String arg1, double a) {
        User user = this.userService.findUserByNickName(p);
        user.addMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, String arg1, double a) {
        User user = this.userService.findUserByNickName(p.getName());
        user.addMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    /* Withdraw from player account */

    @Override
    public EconomyResponse withdrawPlayer(String p, double a) {
        User user = this.userService.findUserByNickName(p);
        user.removeMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, double a) {
        User user = this.userService.findUserByNickName(p.getName());
        user.removeMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String p, String arg1, double a) {
        User user = this.userService.findUserByNickName(p);
        user.removeMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, String arg1, double a) {
        User user = this.userService.findUserByNickName(p.getName());
        user.removeMoney(a);
        return new EconomyResponse(a, user.getMoney(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    /* Not in use */

    @Override
    public EconomyResponse bankBalance(String arg0) {

        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse bankHas(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String arg0, double arg1) {

        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, String arg1) {

        return null;
    }

    @Override
    public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {

        return null;
    }

    @Override
    public boolean createPlayerAccount(String arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(String arg0, String arg1) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {

        return false;
    }

    @Override
    public String currencyNamePlural() {

        return null;
    }

    @Override
    public String currencyNameSingular() {

        return null;
    }

    @Override
    public EconomyResponse deleteBank(String arg0) {

        return null;
    }

    @Override
    public String format(double arg0) {

        return null;
    }

    @Override
    public int fractionalDigits() {

        return 0;
    }

    @Override
    public List<String> getBanks() {

        return null;
    }

    @Override
    public String getName() {

        return null;
    }

    @Override
    public boolean has(String arg0, double arg1) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer arg0, double arg1) {
        return false;
    }

    @Override
    public boolean has(String arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
        return false;
    }

    @Override
    public boolean hasAccount(String arg0) {

        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0) {

        return true;
    }

    @Override
    public boolean hasAccount(String arg0, String arg1) {

        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0, String arg1) {

        return true;
    }

    @Override
    public boolean hasBankSupport() {

        return false;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, String arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
        return null;
    }



}


