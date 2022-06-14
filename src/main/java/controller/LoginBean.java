/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Customer;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
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
    private List<Customer> Customers;
    private FacesContext context;
    private Customer customer;
    @Inject
    private DataBean db;

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

        Customers = db.getCustomerObjectList();
        customer = new Customer();
    }

    public void login() {
        FacesMessage fm;
        // LOGGER.log(Level.INFO, "Eingabe uname: {0} passwort: {1}", new Object[]{this.uname, this.password});
        this.Customers = db.getCustomerObjectList();

        for (Customer c : Customers) {
            LOGGER.log(Level.INFO, "Kunde: {0}passwort: {1}", new Object[]{c.getFkAccid().getAccname(), c.getFkAccid().getAccpwd()});

            if (c.getFkAccid().getAccname().equals(this.uname)) {
                loggedIn = c.getFkAccid().getAccpwd().equals(this.password);
                this.customer = c;
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
     *
     * @return
     */
    public List<Customer> getCustomers() {
        return Customers;
    }

    /**
     *
     * @param c
     */
    public void setCustomers(List<Customer> c) {
        this.Customers = c;
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
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     *
     * @param customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
