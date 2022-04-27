/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.User;
import model.UserRole;

/**
 *
 * @author marku
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable{

    private String uname;
    private String password;
    private ArrayList<User> knownUsers;
    private FacesContext context;


    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {

    }

    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        
        knownUsers = new ArrayList<>();
        knownUsers.add(new User("Markus", "Hartlage", "markus.hartlage@fh-bielefeld.de",
                "hartmark", "Hallo1234".hashCode(), "Herr"));
        knownUsers.add(new User("Bianca", "Beispiel", "biancab@yahoo.com",
                "bibibsp", "GanzGeheim123".hashCode(), "Frau"));
        knownUsers.add(new User("Frank", "Floristiker", "frank@floristik.de",
                "flowerfrank", "L0tusBlume".hashCode(), "Herr", UserRole.ADMIN));
    }
    
    public void login(){
        boolean loggedIn = false;
        FacesMessage fm;
        
        for (User u:knownUsers){
            if (u.getUsername().equals(this.uname)) {
                loggedIn = u.login(this.password);
                break;
            }
        }
        
        if (loggedIn){
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
}
