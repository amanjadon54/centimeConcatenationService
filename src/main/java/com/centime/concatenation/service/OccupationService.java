package com.centime.concatenation.service;

import com.centime.concatenation.model.occupation.FilterNameDto;
import com.centime.concatenation.model.occupation.OccupationBulkRequest;
import com.centime.concatenation.model.occupation.OccupationFilterNameDto;
import com.centime.concatenation.model.entity.OccupationEntity;
import com.centime.concatenation.repository.OccupationRepository;
import com.centime.util.exception.CustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class OccupationService extends AbstractRepoService<OccupationEntity, Integer> {

    @Autowired
    private OccupationRepository occupationRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected JpaRepository<OccupationEntity, Integer> getRepo() {
        return occupationRepository;
    }

    public OccupationEntity findById(Integer id, String logId) {

        OccupationEntity occupationEntity = occupationRepository.findOne(id);
        if (occupationEntity == null) {
            logger.error("No Occupation entity available for id {} {}", id, logId);
            throw new CustomRuntimeException("No data Available", 404, logId);
        }
        return occupationEntity;
    }

    @Transactional
    public boolean saveAll(OccupationBulkRequest occupationBulkRequest, String logId) {
        for (OccupationEntity entity : occupationBulkRequest.getOccupations()) {
            try {
                occupationRepository.save(entity);
            } catch (Exception e) {
                logger.error(e.getMessage() + "for {}", logId);
                throw new CustomRuntimeException("Unable to Save entity", 500, logId);
            }
        }
        return true;
    }

    @Transactional
    public List<OccupationFilterNameDto> getAllByName(String logId) {
        List<OccupationEntity> occupationEntities = occupationRepository.findAll();
        HashMap<String, List<FilterNameDto>> records = new HashMap<>();
        List<OccupationFilterNameDto> filtredData = new LinkedList<>();
        try {
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
            for (Map.Entry<String, List<FilterNameDto>> record : recordSet) {
                OccupationFilterNameDto occupationFilterNameDto = new OccupationFilterNameDto(record.getValue(), record.getKey());
                filtredData.add(occupationFilterNameDto);
            }
        } catch (Exception e) {
            logger.error(e.getMessage() + " logId:{}", logId);
            throw new CustomRuntimeException(e.getMessage(), 500, logId);
        }

        return filtredData;
    }

}
