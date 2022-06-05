/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.User;
import util.DataBean;

/**
 *
 * @author marku
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(RegisterBean.class.getName());
    private HttpSession session;
    private String uname;
    private String password;
    private ArrayList<User> knownUsers;
    private FacesContext context;
    private User user;

    private boolean loggedIn = false;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {

    }

    @PostConstruct
    public void init() {
        LOGGER.info("new LoginBean");
        context = FacesContext.getCurrentInstance();

        session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Login: Session ID: {0}", session.getId());

        DataBean db = new DataBean();
       // db.generateTestUsers();
        knownUsers = db.getUserList();
        user = new User();
    }

    public void login() {
        FacesMessage fm;

        for (User u : knownUsers) {
            if (u.getUsername().equals(this.uname)) {
                loggedIn = u.login(this.password);
                this.user = u;
                break;
            }
        }

        context = FacesContext.getCurrentInstance();
        if (loggedIn) {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg", ": Login erfolgreich!");
            context.addMessage(null, fm);
        } else {
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Fehlschlag", ": Username oder Passwort falsch!");
            context.addMessage(null, fm);
        }

    }

    /**
     * Get the value of knownUsers
     *
     * @return the value of knownUsers
     */
    public ArrayList<User> getKnownUsers() {
        return knownUsers;
    }

    /**
     * Set the value of knownUsers
     *
     * @param knownUsers new value of knownUsers
     */
    public void setKnownUsers(ArrayList<User> knownUsers) {
        this.knownUsers = knownUsers;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the value of string
     *
     * @return the value of string
     */
    public String getUname() {
        return uname;
    }

    /**
     * Set the value of string
     *
     * @param uname new value of uname
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * Get the value of context
     *
     * @return the value of context
     */
    public FacesContext getContext() {
        return context;
    }

    /**
     * Set the value of context
     *
     * @param context new value of context
     */
    public void setContext(FacesContext context) {
        this.context = context;
    }

    /**
     *
     * @return
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     *
     * @param session
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
        LOGGER.log(Level.INFO, "delivering user{0}", user.getUsername());
        return user;
    }

    /**
     * Set the value of user
     *
     * @param user new value of user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the value of loggedIn
     *
     * @return the value of loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Set the value of loggedIn
     *
     * @param loggedIn new value of loggedIn
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
