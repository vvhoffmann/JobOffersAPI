package com.hoffmann.joboffersapi.domain.offercrud;

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
import java.util.stream.StreamSupport;

class InMemoryOfferRepository implements OfferRepository {

    Map<String, Offer> db = new ConcurrentHashMap<>();

    @Override
    public <S extends Offer> S save(final S entity) {
        if(db.values().stream().anyMatch(o -> o.offerUrl().equals(entity.offerUrl())))
            throw new OfferDuplicateException(entity.offerUrl());

        UUID id = UUID.randomUUID();
        Offer offerToSave = Offer.builder()
                .id(id.toString())
                .offerUrl(entity.offerUrl())
                .companyName(entity.companyName())
                .salary(entity.salary())
                .position(entity.position())
                .build();
        db.put(offerToSave.id(),offerToSave);
        return (S) offerToSave;
    }

    @Override
    public <S extends Offer> List<S> saveAll(final Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(),false)
                .map(this::save)
                .toList();
    }

    @Override
    public List<Offer> findAll() {
        return db.values().stream().toList();
    }

    @Override
    public List<Offer> findAllById(final Iterable<String> strings) {
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
    public void delete(final Offer entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(final Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Offer> findById(final String offerId) {
        return Optional.ofNullable(db.get(offerId));
    }

    @Override
    public boolean existsById(final String s) {
        return false;
    }

    @Override
    public boolean existsByOfferUrl(final String url) {
        return db.values().stream()
                .anyMatch(o -> o.offerUrl().equals(url));
    }

    @Override
    public <S extends Offer> S insert(final S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public <S extends Offer> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Offer> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Offer> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Offer> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<Offer> findAll(final Pageable pageable) {
        return null;
    }
}