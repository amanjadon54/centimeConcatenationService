package com.centime.concatenation.service;

import com.centime.concatenation.model.FilterNameDto;
import com.centime.concatenation.model.OccupationBulkRequest;
import com.centime.concatenation.model.OccupationFilterNameDto;
import com.centime.concatenation.model.entity.OccupationEntity;
import com.centime.concatenation.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public List<OccupationFilterNameDto> getAllByName() {
        List<OccupationEntity> occupationEntities = occupationRepository.findAll();
        HashMap<String, List<FilterNameDto>> records = new HashMap<>();

        for (OccupationEntity entity : occupationEntities) {
            for (OccupationEntity innerEntity : occupationEntities) {
                if (innerEntity.getParentId() == entity.getId()) {
                    if (records.containsKey(entity.getName())) {
                        records.get(entity.getName()).add(new FilterNameDto(innerEntity.getName()));
                    } else {
                        LinkedList<FilterNameDto> dto = new LinkedList<>();
                        dto.add(new FilterNameDto(innerEntity.getName()));
                        records.put(entity.getName(), dto);
                    }
                }
            }

        }

        Set<Map.Entry<String, List<FilterNameDto>>> recordSet = records.entrySet();
        List<OccupationFilterNameDto> filtredData = new LinkedList<>();
        for (Map.Entry<String, List<FilterNameDto>> record : recordSet) {
            OccupationFilterNameDto occupationFilterNameDto = new OccupationFilterNameDto(record.getValue(), record.getKey());
            filtredData.add(occupationFilterNameDto);
        }

        return filtredData;
    }

}
