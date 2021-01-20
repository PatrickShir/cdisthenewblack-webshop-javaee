package ejb;

import javax.ejb.Local;

@Local
public interface LoginBeanLocal {
    User validateUser(String userName, String password);

    boolean usernameExist(String username);

    void addUser(User user);
}
