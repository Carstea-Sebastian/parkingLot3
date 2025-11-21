package org.parkinglot.parkinglot.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.parkinglot.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();

        return users.stream()
                .map(u -> new UserDto(u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }
}
