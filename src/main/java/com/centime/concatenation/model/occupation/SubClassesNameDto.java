package com.centime.concatenation.model.occupation;

import com.centime.concatenation.model.occupation.FilterNameDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
public class SubClassesNameDto {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Sub Classes")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SubClassesNameDto> subClasses;

}
