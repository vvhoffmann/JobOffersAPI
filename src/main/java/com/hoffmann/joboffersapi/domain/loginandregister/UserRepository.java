package com.hoffmann.joboffersapi.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);
}
