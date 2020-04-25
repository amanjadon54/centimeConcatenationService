package com.centime.concatenation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
public class OccupationBulkRequest {
    private List<OccupationEntity> occupations;
}
