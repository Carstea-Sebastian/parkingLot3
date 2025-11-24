package com.parking.parkinglot.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    @PersistenceContext
    EntityManager em;
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    private List<UserDto> copyUsers(List<User> users) {
        List<UserDto> copyUsers = new ArrayList<>();
        for (User u : users) {
            UserDto userDto = new UserDto(u.getId(), u.getEmail(), u.getUsername());
            copyUsers.add(userDto);
        }
        return copyUsers;
    }

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> us = query.getResultList();
        return copyUsers(us);
    }
}