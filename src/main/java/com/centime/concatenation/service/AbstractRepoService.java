package com.centime.concatenation.service;

import com.centime.util.exception.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractRepoService<T, U> {

    @Autowired
    protected abstract JpaRepository<T, Integer> getRepo();

    public T createEntity(T entity) {
        T createdEntity;
        try {
            createdEntity = getRepo().save(entity);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error while creating entity in DB: " + entity.toString() + " : " + e.getMessage());
        }
        return createdEntity;
    }

    public List<T> createEntities(List<T> entities) {
        return getRepo().save(entities);
    }

    public List<T> updateEntities(Iterable<T> entities) {
        return getRepo().save(entities);
    }

    public T getEntityById(Integer id, String logId) {
        T entity;
        try {
            entity = getRepo().findOne(id);
            if (entity == null) {
                throw new CustomRuntimeException(
                        "Error while retrieving entity from DB: " + id + " : ", 500, logId);

            }
        } catch (Exception e) {
            throw new CustomRuntimeException(
                    "Error while retrieving entity from DB: " + id + " : " + e.getMessage(), 500, logId);
        }
        return entity;
    }

    public List<T> getAllEntities() {
        List<T> entities;
        try {
            entities = getRepo().findAll();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error while retrieving entities from DB:" + e.getMessage());
        }
        return entities;
    }

    public T updateEntity(T entity) {
        T updatedEntity;
        try {
            updatedEntity = getRepo().save(entity);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error while updating entity from DB: " + entity.toString() + " : " + e.getMessage());
        }
        return updatedEntity;
    }

    public void deleteEntity(T entity) {
        try {
            getRepo().delete(entity);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error while deleting entity from DB: " + entity.toString() + " : " + e.getMessage());
        }
    }
}
