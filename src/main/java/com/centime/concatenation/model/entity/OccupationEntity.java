package com.centime.concatenation.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "occupation")
public class OccupationEntity {

    private Integer id;

    @Column(name = "parent_id", nullable = false)
    @JsonProperty("parentId")
    private Integer parentId;
    private String name;
    private String color;

    public OccupationEntity() {

    }

    public OccupationEntity(Integer id, Integer parentId, String name, String color) {
        this.parentId = parentId;
        this.name = name;
        this.color = color;
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "color", nullable = false)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", Name=" + name + ", color=" + color + ", parentId=" + parentId
                + "]";
    }
}
