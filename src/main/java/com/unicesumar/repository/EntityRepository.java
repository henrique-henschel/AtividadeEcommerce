package com.unicesumar.repository;

import com.unicesumar.model.Entity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityRepository<E extends Entity> {
    void save(E entity);
    Optional<E> findById(UUID id);
    List<E> findAll();
    void deleteById(UUID id);
}
