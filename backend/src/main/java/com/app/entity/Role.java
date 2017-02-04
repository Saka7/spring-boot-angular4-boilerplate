package com.app.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String label;

    public Role(String label) {
        this.label = label;
    }

}
