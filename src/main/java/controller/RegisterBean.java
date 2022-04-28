/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import model.User;
import util.DataBean;

/**
 *
 * @author marku
 */
@Named(value = "registerBean")
@RequestScoped
public class RegisterBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(RegisterBean.class.getName());
    private static final String NAMEREGEX = "^[a-zA-Z]+$";
    private static final String NAMEMSG
            = "Es sind nur Klein- und Großbuchstaben von a-z erlaubt.";
    private static final String PWDREGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
    private static final String PWDMSG
            = "Das Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Groß- und Kleinbuchstaben, sowie eine Ziffer enthalten!";

    private Map<String, String> greetings = new HashMap<>();
    private String greeting;
    @Pattern(regexp = NAMEREGEX, message = NAMEMSG)
    private String fname;
    @Pattern(regexp = NAMEREGEX, message = NAMEMSG)
    private String lname;
    @NotNull
    private String uname;
    @Pattern(regexp = PWDREGEX, message = PWDMSG)
    private String password;
    @Email(message = "email should match")
    private String email;
    private boolean registered;
    private FacesContext context;
    @Inject
    private DataBean dbean;

    private ArrayList<User> users;


    @PostConstruct
    public void init() {
        greetings = new HashMap<>();
        greetings.put("Herr", "Herr");
        greetings.put("Frau", "Frau");
        setGreeting(greetings.get("Frau"));

        context = FacesContext.getCurrentInstance();

        registered = false;

        users = dbean.getUserList();
        
        LOGGER.log(Level.INFO, "new registerBean {0}", users);
    }

    public String process() {
        FacesMessage fm;
        registered = true;
        // username is allready taken
        try {
            for (User u : users) {
                try{
                if (u.getUsername().equals(this.uname)
                        || (u.getEmail().equals(this.email) && !this.email.isEmpty())) {
                    registered = false;
                }
                } catch(NullPointerException e) {
                    LOGGER.log(Level.WARNING, "users was not null!");
                }
            }
        } catch (NullPointerException e) {
            if (users == null){
            LOGGER.log(Level.WARNING, "users was null!");
            }
        }

        if (!registered) {
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Username oder Email bereits vergeben.");
            context.addMessage(null, fm);
            return null;
        } else if (!password.matches(PWDREGEX)) { // Kontrolle auf zulaessiges Passwort
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Passwort nicht sicher.");
            context.addMessage(null, fm);
            return null;
        } else {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg",
                    ": Registrieren erfolgreich!");
            context.addMessage(null, fm);
            return "login.xhtml";
        }
    }

    /**
     *
     * @param ev
     */
    public void pwdAjaxListener(AjaxBehaviorEvent ev) {
        FacesMessage fm;
        if (!context.isValidationFailed()) {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg", ": Passwort formal korrekt!");
            UIComponent uic = UIComponent.getCurrentComponent(context);
            context.addMessage(uic.getClientId(), fm);
        }
    }

    /**
     * Get the value of lname
     *
     * @return the value of lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Set the value of lname
     *
     * @param lname new value of lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Get the value of fname
     *
     * @return the value of fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Set the value of fname
     *
     * @param fname new value of fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Get the value of greeting
     *
     * @return the value of greeting
     */
    public String getGreeting() {
        return greeting;
    }

    /**
     * Set the value of greeting
     *
     * @param greeting new value of greeting
     */
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    /**
     * Get the value of email
     *
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the value of email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the value of uname
     *
     * @return the value of uname
     */
    public String getUname() {
        return uname;
    }

    /**
     * Set the value of uname
     *
     * @param uname new value of uname
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public String getPassword() {
        // System.out.println(password);
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        // System.out.println(password);
        this.password = password;
    }

    /**
     * Get the value of registered
     *
     * @return the value of registered
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * Set the value of registered
     *
     * @param registered new value of registered
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**
     * Get the value of greetings
     *
     * @return the value of greetings
     */
    public Map<String, String> getGreetings() {
        return greetings;
    }
    /**
     * Get the value of dbean
     *
     * @return the value of dbean
     */
    public DataBean getDbean() {
        return dbean;
    }

    /**
     * Set the value of dbean
     *
     * @param dbean new value of dbean
     */
    public void setDbean(DataBean dbean) {
        this.dbean = dbean;
    }
}
