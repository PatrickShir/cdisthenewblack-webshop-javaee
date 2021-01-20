package ejb;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless(name = "LoginEJB")
public class LoginBean implements LoginBeanLocal {

    @PersistenceContext(name = "webshopUnit")
    EntityManager entityManager;

    private String name;

    public LoginBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public User validateUser(String userName, String password) {

        for (User user : getAllUsers()) {
            if (user.getUserName().equalsIgnoreCase(userName)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

    private List<User> getAllUsers() {
    return entityManager.createQuery("SELECT o FROM User o", User.class).getResultList();
    }

    @Override
    public boolean usernameExist(String username) {
        for (User user:getAllUsers()) {
            if (user.getUserName().equalsIgnoreCase(username)) {
               return true;
            }
        }
        return false;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }
}


