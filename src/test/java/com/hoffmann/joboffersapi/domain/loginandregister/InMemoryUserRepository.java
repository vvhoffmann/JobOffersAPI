package com.hoffmann.joboffersapi.domain.loginandregister;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryUserRepository implements UserRepository {

    Map<String, User> db = new ConcurrentHashMap<>();


    @Override
    public Optional<User> findByUsername(final String username) {
        return Optional.ofNullable(db.get(username));
    }

    @Override
    public <S extends User> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends User> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(final S entity) {
        UUID id = UUID.randomUUID();
        User savedUser = User.builder()
                .username(entity.username())
                .password(entity.password())
                .id(id.toString())
                .build();
        db.put(savedUser.username(), savedUser);
        return (S) savedUser;
    }

    @Override
    public <S extends User> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(final String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public List<User> findAllById(final Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(final String s) {

    }

    @Override
    public void delete(final User entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(final Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(final Pageable pageable) {
        return null;
    }
}