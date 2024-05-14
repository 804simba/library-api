package com.simba.libraryapi.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Table(
        name = "patrons",
        uniqueConstraints = { @UniqueConstraint(name = "booksUniqueConstraint", columnNames = {"email_address", "phone_number"})}
)
@Entity
public class Patron extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "patron_roles",
            joinColumns = @JoinColumn(name = "patron_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @Override
    public int hashCode() {
        return (this.getId() != null ? this.getId().hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Patron) && ((Patron) obj).getId() != null;
    }
}
