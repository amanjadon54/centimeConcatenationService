package com.centime.concatenation.service;

import com.centime.concatenation.model.occupation.FilterNameDto;
import com.centime.concatenation.model.occupation.OccupationBulkRequest;
import com.centime.concatenation.model.occupation.SubClassesNameDto;
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
    public List<SubClassesNameDto> getAllByName(String logId) {
        List<OccupationEntity> occupationEntities = occupationRepository.findAll();
        HashMap<String, SubClassesNameDto> records = new HashMap<>();
        List<SubClassesNameDto> filtredData = new LinkedList<>();
        HashMap<Integer, OccupationEntity> recordWithIdentity = new HashMap<>();
        for (OccupationEntity entity : occupationEntities) {
            recordWithIdentity.put(entity.getId(), entity);
        }

        for (OccupationEntity entity : occupationEntities) {
            if (entity.getParentId() == 0) {
                records.put(entity.getName(), new SubClassesNameDto(entity.getName(), new LinkedList<>()));
            } else if (records.containsKey(recordWithIdentity.get(entity.getParentId()))) {
                SubClassesNameDto values = records.get(recordWithIdentity.get(entity.getParentId()));
                values.getSubClasses().add(new SubClassesNameDto(entity.getName(), null));
            } else {
                records = arrangeNestedParent(entity, recordWithIdentity.get(entity.getParentId()), recordWithIdentity,
                        records);
            }

        }

        Set<Map.Entry<String, SubClassesNameDto>> recordSet = records.entrySet();
        for (Map.Entry<String, SubClassesNameDto> record : recordSet) {
            filtredData.add(record.getValue());
        }

        return filtredData;

    }

    private HashMap<String, SubClassesNameDto> arrangeNestedParent(OccupationEntity current, OccupationEntity parent,
                                                                   HashMap<Integer, OccupationEntity> recordWithIdentity,
                                                                   HashMap<String, SubClassesNameDto> records) {
        if (!recordWithIdentity.containsKey(parent.getParentId())) {
            SubClassesNameDto values = records.get(parent.getName());
            for (SubClassesNameDto subClassesNameDto : values.getSubClasses()) {
                if (subClassesNameDto.getName().equals(recordWithIdentity.get(current.getParentId()).getName())) {
                    if (subClassesNameDto.getSubClasses() == null) {
                        LinkedList<SubClassesNameDto> subClassesNameDtoLinkedList = new LinkedList<>();
                        subClassesNameDtoLinkedList.add(new SubClassesNameDto(current.getName(), null));
                        subClassesNameDto.setSubClasses(subClassesNameDtoLinkedList);
                        return records;
                    } else {
                        subClassesNameDto.getSubClasses().add(new SubClassesNameDto(current.getName(), null));
                        return records;
                    }
                }
            }

            values.getSubClasses().add(new SubClassesNameDto(current.getName(), null));
            return records;
        }

        return arrangeNestedParent(current, recordWithIdentity.get(parent.getParentId()), recordWithIdentity, records);
    }

}
