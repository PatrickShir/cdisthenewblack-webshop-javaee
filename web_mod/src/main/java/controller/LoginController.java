package controller;

import ejb.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginController implements Serializable {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    @EJB
    private LoginBeanLocal loginBeanLocal;
    @EJB
    private CreateDataBeanLocal createDataBeanLocal;
    @EJB
    private CurrentUserBeanLocal currentUserBeanLocal;

    @PostConstruct
    public void init() {
        createDataBeanLocal.fillDb();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CurrentUserBeanLocal getCurrentUserBeanLocal() {
        return currentUserBeanLocal;
    }

    public void setCurrentUserBeanLocal(CurrentUserBeanLocal currentUserBeanLocal) {
        this.currentUserBeanLocal = currentUserBeanLocal;
    }

    public String validateUsernamePassword() {
        User user = loginBeanLocal.validateUser(username, password);
        if (user != null) {
            currentUserBeanLocal.setCurrentUser(user);
            return user.getRole() == Role.ADMIN ? "adminUserOverview" : "shop";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Password",
                            "Please enter correct username and Password"));
            return "login";
        }

    }

    public String registerUser() {
        User user = new User(this.username, this.firstName, this.lastName, this.password, Role.CUSTOMER);

        if (loginBeanLocal.usernameExist(user.getUserName())) {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Username is already taken",
                            "Please enter another username"));
            return "registerUser";
        } else {
            loginBeanLocal.addUser(user);
            currentUserBeanLocal.setCurrentUser(user);
            return "shop";
        }
    }

}
