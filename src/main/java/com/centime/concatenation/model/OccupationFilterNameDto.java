package com.centime.concatenation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class OccupationFilterNameDto {
    @JsonProperty("Sub Classes")
    private List<FilterNameDto> subClasses;

    @JsonProperty("Name")
    private String name;


}
