package com.parking.parkinglot.ejb;
import com.parking.parkinglot.entities.User;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {
    @PersistenceContext
    EntityManager entityManager;
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @Inject
    PasswordBean passwordBean;

    private List<UserDto> copyUsersToDto(List<User> users) {
        List<UserDto> dtos = new ArrayList<>();
        for(User user : users){
            UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getUsername());
            dtos.add(userDto);
        }
        return dtos;
    }

    public List<UserDto> findAllUsers(){
        LOG.info("findAllUsers");
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        }catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
}
