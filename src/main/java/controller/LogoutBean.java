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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marku
 */
@Named(value = "logoutBean")
@SessionScoped
public class LogoutBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(RegisterBean.class.getName());

    private HttpSession session;

    private FacesContext context;

    @PostConstruct
    public void init() {
        LOGGER.info("new LoginBean");
        context = FacesContext.getCurrentInstance();

        session = (HttpSession) context.getExternalContext().getSession(false);
    }

    /**
     * Creates a new instance of LogoutBean
     */
    public LogoutBean() {
    }

    public void logout() {
        context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        session = (HttpSession) context.getExternalContext().getSession(true);
        LOGGER.log(Level.INFO, "Logout: Session ID: {0}, logged out!", session.getId());
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "logout", "Auf Wiedersehen!");
        context.addMessage(null, fm);
    }

    /**
     * Get the value of LOGGER
     *
     * @return the value of LOGGER
     */
    public static Logger getLOGGER() {
        return LOGGER;
    }

    /**
     * Get the value of session
     *
     * @return the value of session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * Set the value of session
     *
     * @param session new value of session
     */
    public void setSession(HttpSession session) {
        this.session = session;
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
