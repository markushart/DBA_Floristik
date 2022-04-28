/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.User;
import util.DataBean;

/**
 *
 * @author marku
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(RegisterBean.class.getName());
    private User user;
    
    private ArrayList<User> knownUsers;


    @PostConstruct
    public void init() {
        LOGGER.info("new LoginBean");
        FacesContext context = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Login: Session ID: {0}", session.getId());
        
        // get the users from the databean
        requestKnownUsers();
        
    }
    
   /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }
    
    /**
     * create a databean and querry all knwon users
     */
    public void requestKnownUsers(){
        DataBean db = new DataBean();
        knownUsers = db.getUserList();
    }
    
    public boolean loginUser(String uname, String pwd){
        boolean loginSuccessfull = false;
        for (User u : this.knownUsers){
            if (u.getUsername().equals(uname)) {
                this.user = u;
                loginSuccessfull = this.user.login(pwd);
            }
        }
        return loginSuccessfull;
    }

    /**
     * Get the value of user
     *
     * @return the value of user
     */
    public User getUser() {
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
     * Get the value of knownUsers
     *
     * @return the value of knownUsers
     */
    public ArrayList<User> getKnownUsers() {
        return knownUsers;
    }
    
}
