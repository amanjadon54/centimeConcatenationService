package com.centime.concatenation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Data
public class FilterNameDto {
    @JsonProperty("Name")
    private String name;
}
