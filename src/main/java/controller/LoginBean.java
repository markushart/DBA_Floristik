/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marku
 */
@Named(value = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(RegisterBean.class.getName());
    private HttpSession session;
    private String uname;
    private String password;
    private FacesContext context;

    @Inject
    private UserBean ubean;

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

    }

    /**
     * get known users and compare their usernames to username try to log in
     * matched username and report success / failure
     */
    public void login() {
        FacesMessage fm;
        LOGGER.log(Level.INFO, "Login: uname: {0} password: {1}", new Object[]{uname, password});
        boolean loggedIn = ubean.loginUser(uname, password);

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
     * Get the value of ubean
     *
     * @return the value of ubean
     */
    public UserBean getUbean() {
        return ubean;
    }

    /**
     * Set the value of ubean
     *
     * @param ubean new value of ubean
     */
    public void setUbean(UserBean ubean) {
        if (ubean == null) {
            LOGGER.log(Level.WARNING, "ubean was null!");
        } else {
            LOGGER.info("Login: ubean was set successfully!");
            this.ubean = ubean;
        }
    }

}
