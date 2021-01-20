package ejb;

import javax.ejb.Stateful;

@Stateful(name = "CurrentUserBeanEJB")
public class CurrentUserBean implements CurrentUserBeanLocal {
    public CurrentUserBean() {
    }

    private User currentUser;

    @Override
    public String getUserFullName() {
        return currentUser.getFirstName() + " " + currentUser.getLastName();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
