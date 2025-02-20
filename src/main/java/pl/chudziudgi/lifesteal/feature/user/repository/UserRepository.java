package pl.chudziudgi.lifesteal.feature.user.repository;

import pl.chudziudgi.lifesteal.database.DatabaseRepository;
import pl.chudziudgi.lifesteal.feature.user.User;

public class UserRepository extends DatabaseRepository<String, User> {

    public UserRepository() {
        super(User.class, "uuid", "users");
    }
}
