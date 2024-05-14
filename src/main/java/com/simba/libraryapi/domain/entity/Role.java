package com.simba.libraryapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "roles_tbl")
@Getter
@Setter
public class Role extends BaseEntity {
    @Column(name = "role_name")
    private String name;

    @Column(name = "role_description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "role_privileges_mapping", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Permission> permissions;
}
