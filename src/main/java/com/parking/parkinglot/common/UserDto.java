package com.parking.parkinglot.common;

public class UserDto {
    private Long id;
    private String email;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto(Long id, String email,String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
