package ejb;

public interface CurrentUserBeanLocal {
    String getUserFullName();
    void setCurrentUser(User currentUser);
    User getCurrentUser();

}
