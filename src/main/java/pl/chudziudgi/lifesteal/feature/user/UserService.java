package pl.chudziudgi.lifesteal.feature.user;

import pl.chudziudgi.lifesteal.database.UpdateType;
import pl.chudziudgi.lifesteal.feature.user.repository.UserRepository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private final UserRepository userRepository;

    public final Map<UUID, User> userConcurrentHashMap = new ConcurrentHashMap<>();


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Collection<User> users() {
        return this.userConcurrentHashMap.values();
    }

    public void addUser(User user) {
        this.userConcurrentHashMap.put(UUID.fromString(user.getUuid()), user);
    }

    public void createUser(User user){
        this.userConcurrentHashMap.put(UUID.fromString(user.getUuid()), user);
        userRepository.update(user, user.getId(), UpdateType.CREATE);
    }

    public void saveAllUsers() {
        this.userConcurrentHashMap.forEach((s, clan) -> {
                    saveUser(clan);
                }
        );
    }

    public void saveUser(User user){
        userRepository.update(user, user.getId(), UpdateType.UPDATE);
    }

    public void removeUser(User user) {
        this.userConcurrentHashMap.remove(UUID.fromString(user.getId()));
    }

    public User findUserByNickName(String nickName){
        return this.userConcurrentHashMap.values()
                .stream()
                .filter(user -> user.getNickName().contains(nickName))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUUID(UUID uuid){
        return this.userConcurrentHashMap.get(uuid);
    }
}
