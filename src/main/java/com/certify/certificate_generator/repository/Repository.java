package com.certify.certificate_generator.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Repository implements JpaRepository<String, String> {
    @Override
    public void flush() {

    }

    @Override
    public <S extends String> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends String> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<String> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public String getOne(String s) {
        return "";
    }

    @Override
    public String getById(String s) {
        return "";
    }

    @Override
    public String getReferenceById(String s) {
        return "";
    }

    @Override
    public <S extends String> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends String> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends String> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends String> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends String> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends String> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends String, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends String> S save(S entity) {
        return null;
    }

    @Override
    public <S extends String> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<String> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<String> findAll() {
        return List.of();
    }

    @Override
    public List<String> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(String entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends String> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<String> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<String> findAll(Pageable pageable) {
        return null;
    }
}
