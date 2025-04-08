package pl.chudziudgi.lifesteal.feature.top;

import pl.chudziudgi.lifesteal.feature.user.User;
import pl.chudziudgi.lifesteal.feature.user.UserService;
import pl.chudziudgi.lifesteal.util.DecimalUtil;
import pl.chudziudgi.lifesteal.util.DurationUtil;
import pl.chudziudgi.lifesteal.util.MessageUtil;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopManager {

    private final UserService userService;

    public TopManager(UserService userService) {
        this.userService = userService;
    }

    public String getTopUserName(List<User> topList, String params, String prefix, Function<User, Object> valueGetter) {
        try {
            int index = Integer.parseInt(params.replace(prefix, "")) - 1;
            if (index >= 0 && index < topList.size()) {
                User topUser = topList.get(index);
                Object value = valueGetter.apply(topUser);

                String formattedValue;

                switch (prefix) {
                    case "topMoney_", "topVpln_" -> {
                        double money = value instanceof Number ? ((Number) value).doubleValue() : 0.0;
                        formattedValue = DecimalUtil.getFormat(money);
                    }
                    case "topTime_" -> {
                        long time = value instanceof Number ? ((Number) value).longValue() : 0L;
                        formattedValue = DurationUtil.format(Duration.ofSeconds(time));
                    }
                    default -> {
                        formattedValue = value != null ? value.toString() : "0";
                    }
                }

                return MessageUtil.smallText("&f" + (index + 1) + ". &7" + topUser.getNickName() + " &8- &f" + formattedValue);
            }
        } catch (NumberFormatException ignored) {
        }
        return MessageUtil.smallText("&cBrak");
    }




    public List<User> get16UsersMoneyTop() {
        return userService.userConcurrentHashMap.values().stream()
                .sorted(Comparator.comparingDouble(User::getMoney).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }

    public List<User> get16UsersSpendTime() {
        return userService.userConcurrentHashMap.values().stream()
                .sorted(Comparator.comparingInt(User::getSpentTime).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }

    public List<User> get16UsersKills() {
        return userService.userConcurrentHashMap.values().stream()
                .sorted(Comparator.comparingInt(User::getKill).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }

    public List<User> get16UsersVpln() {
        return userService.userConcurrentHashMap.values().stream()
                .sorted(Comparator.comparingDouble(User::getVPln).reversed())
                .limit(16)
                .collect(Collectors.toList());
    }
}
