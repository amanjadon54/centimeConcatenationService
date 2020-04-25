package com.centime.concatenation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
public class OccupationRequest {
    private long id;
    private long parentId;
    private String color;
    private String name;
}
