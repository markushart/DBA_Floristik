/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Account;
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
    private Account currAccount;
    
    @Inject
    private DataBean db;

    private boolean loggedIn = false;
    private boolean isAdmin = false;
    // private Customer customer;

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
    }

    public void login() {
        FacesMessage fm;
        // LOGGER.log(Level.INFO, "Eingabe uname: {0} passwort: {1}", new Object[]{this.uname, this.password});

        Account acc = db.findAccountForAccountName(uname);
        LOGGER.log(Level.INFO, "search for name {0} returned account with name {1} and id {2}!",
                new Object[]{this.uname, acc.getAccname(), acc.getAccid()});

        // accountname or accid is null if uname was not found
        if (acc.getAccname() == null || acc.getAccid() == null) {
            LOGGER.log(Level.WARNING, "Account not found!");
        } else {
            LOGGER.log(Level.INFO, "Account was found!");

            if (acc.getAccpwd().equals(this.password)) {
                this.currAccount = acc;
                loggedIn = true;
                
            }
            
            if (acc.getAcctype().equals("Admin")) {
                System.out.println("Ist Administrator");
                isAdmin = true;
                
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
    public Account getCurrAccount() {
        return currAccount;
    }

    /**
     *
     * @param currAccount
     */
    public void setCurrAccount(Account currAccount) {
        this.currAccount = currAccount;
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

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    

}
