package com.centime.concatenation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class OccupationByNameDto {

    private List<OccupationFilterNameDto> filtredData;

}
