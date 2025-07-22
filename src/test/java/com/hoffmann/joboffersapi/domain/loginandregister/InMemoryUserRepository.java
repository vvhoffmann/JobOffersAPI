package com.hoffmann.joboffersapi.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryUserRepository implements UserRepository {

    Map<String, User> db = new ConcurrentHashMap<>();

    @Override
    public User save(final User user) {
        UUID id = UUID.randomUUID();
        User savedUser = User.builder()
                .login(user.login())
                .password(user.password())
                .id(id.toString())
                .build();
        db.put(savedUser.login(), savedUser);
        return savedUser;
    }

    @Override
    public Optional<User> findByLogin(final String login) {
        return Optional.ofNullable(db.get(login));
    }

    @Override
    public boolean existsByLogin(final String login) {
        return db.containsKey(login);
    }
}