package com.centime.concatenation.repository;

import com.centime.concatenation.model.entity.OccupationEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Integer> {

    public List<OccupationEntity> findAll();

    public <S extends OccupationEntity> S save(S s);

    public OccupationEntity findOne(Integer integer);

    public OccupationEntity getOne(Integer integer);

    @Query("select distinct v.parentId from OccupationEntity v")
    public List<Integer> findDistinctName();
}
