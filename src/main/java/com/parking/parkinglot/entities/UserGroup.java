package com.parking.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "usergroup", nullable = false)
    private String usergroup;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) { this.username = username;}
    public String getUsername() { return username;}
    public void setUserGroup(String userGroup){ this.usergroup = userGroup;}
    public String getUserGroup() { return usergroup;}
}