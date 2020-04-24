package com.centime.concatenation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Getter
public class NameRequest {
    @NotNull
    @JsonProperty(value = "Name", required = true)
    private String name;

    @NotNull
    @JsonProperty(value = "Sirname", required = true)
    private String sirNmae;

}
