package com.simba.libraryapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "privileges_tbl")
@Getter
@Setter
public class Permission extends BaseEntity {
    @Column(name = "privilege_name")
    private String name;

    @ManyToMany(targetEntity = Role.class)
    private Collection<Role> roles;
}
