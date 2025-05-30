package community.user.application.repository;

import community.user.application.interfaces.UserRepository;
import community.user.domain.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {
    private final Map<Long, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        // id가 이미 있다면 업데이
        if(user.getId() != null) {
            store.put(user.getId(), user);
        }
        Long id = store.size() + 1L;
        User newuser = new User(id, user.getInfo());

        store.put(id, newuser);
        return newuser;
    }

    @Override
    public User findById(Long id) {
        return store.get(id);
    }
}


