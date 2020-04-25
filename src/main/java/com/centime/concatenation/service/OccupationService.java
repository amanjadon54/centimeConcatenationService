package com.centime.concatenation.service;

import com.centime.concatenation.model.entity.OccupationBulkRequest;
import com.centime.concatenation.model.entity.OccupationEntity;
import com.centime.concatenation.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OccupationService extends AbstractRepoService<OccupationEntity, Integer> {

    @Autowired
    private OccupationRepository occupationRepository;

    @Override
    protected JpaRepository<OccupationEntity, Integer> getRepo() {
        return occupationRepository;
    }

    public OccupationEntity findById(Integer id) {
        return occupationRepository.findOne(id);
    }

    @Transactional
    public boolean saveAll(OccupationBulkRequest occupationBulkRequest) {
        for (OccupationEntity entity : occupationBulkRequest.getOccupations()) {
            occupationRepository.save(entity);
        }
        return true;
    }

    @Transactional
    public List<OccupationEntity> getAllByName() {
        List<OccupationEntity> occupationEntities = occupationRepository.findOccupationByFilter();
        return occupationEntities;
    }

}
