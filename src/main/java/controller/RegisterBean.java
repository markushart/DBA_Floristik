/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import com.dba_floristik.Account;
import com.dba_floristik.Adress;
import com.dba_floristik.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import util.DataBean;
import java.util.regex.*;
import static java.util.regex.Pattern.matches;

/**
 * Name: RegisterBean Aufgabe: Repräsentierung Backend für das Registrieren
 * Version: 2.0 Letzte Änderung: 24.06.2022 Realisierung Markus Hartlage und
 * Sascha Nickel
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
    // private static final String CPHONEMSG = "Die Telefonnummer darf nur Zahlen enthalten!";

    private final Map<String, String> greetings = new HashMap<>(2);
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
    private static final String DEFAULTEMAIL = "x@x.x";

    // Telefonnummer darf maximal 20 stellen haben und kein Bruch sein
    // @Digits(integer = 20, fraction = 0, message = CPHONEMSG)
    private String cphone;
    private static final String DEFAULTCPHONE = "xxxxxxxxxx 0";

    private Date birthdate = new Date();

    /* Adressinformationen */
    private final Map<String, String> supportedCountries = new HashMap<>(5);

    private final Map<String, String> supportedFedStates = new HashMap<>(14);

    private String street;

    private static final String DEFAULTSTREET = "xxxxxxxxxx 0";

    private String city;

    private static final String DEFAULTCITY = "xxxxxxxxxx";

    private String citycode;

    private static final String DEFAULTCITYCODE = "000000";

    private String country;

    private String fedState;

    /* Adressinformationen Ende*/
    private boolean not_registered;
    private FacesContext context;

    @Inject
    private DataBean dbean;

    private List<Customer> users;

    @PostConstruct
    public void init() {

        this.init_maps();

        context = FacesContext.getCurrentInstance();

        not_registered = false;

        users = dbean.getCustomerObjectList();

        // LOGGER.log(Level.INFO, "new registerBean {0}", users);
    }

    /**
     * Regestriert User bzw. prüft die Regestrierungsdaten
     *
     *
     * @return 
     */
    public String process() {
        FacesMessage fm;
        not_registered = true;

        // fill empty parameters with default values
        setDefaultForEmptyAttributes();

        if (users == null) {
            LOGGER.log(Level.WARNING, "Userlist was null!");
            return null;
        }

        // check if username is allready taken
        for (Customer c : users) {

            Account a = c.getFkAccid();
            if (a.getAccname().equals(this.uname)
                    || (c.getCemail().equals(this.email)
                    && !(this.email.equals(DEFAULTEMAIL)))) {

                fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                        ": Die Email Adresse oder der Username wird bereits verwendet.");
                context.addMessage(null, fm);
                return null;
            }
        }

        try {
            if (not_registered) {
                // IDs werden automatisch vergeben
                Account a = new Account(0, this.uname, this.password, "USER");
                Customer c = new Customer(0, this.fname, this.lname, this.greeting, this.email, this.cphone, this.birthdate);
                LOGGER.log(Level.INFO, "cid: {0}", c.getCid());
                Adress add = new Adress(c.getCid(), this.street, this.city, this.fedState, this.citycode, this.country, new Date());
                Collection<Adress> add_coll = new ArrayList<>();
                add_coll.add(add);
                not_registered = this.dbean.persistCustomer(c, a, add_coll);
                c.setAdressCollection(add_coll);
            }
        } catch (ConstraintViolationException ex) {
            // 
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Eine der Eingaben entsprach nicht den Eingabebeschränkungen.");
            context.addMessage(null, fm);
            return null;
        }

        if (!not_registered) {
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Fehlschlag bei der Registrierung.");
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
     * Ajax-listener für Passwörter
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
     * Ajax-listener für Nummer
     *
     * @param ev
     */
    public void phoneAjaxListener(AjaxBehaviorEvent ev) {
        FacesMessage fm;
        if (!context.isValidationFailed()) {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg", ": Nummer formal korrekt!");
            UIComponent uic = UIComponent.getCurrentComponent(context);
            context.addMessage(uic.getClientId(), fm);
        }
    }

    /**
     * Ajax-listener für Name
     *
     * @param ev
     */
    public void unameAjaxListener(AjaxBehaviorEvent ev) {
        FacesMessage fm;
        if (!context.isValidationFailed()) {
            fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Erfolg", ": Login formal korrekt!");
            UIComponent uic = UIComponent.getCurrentComponent(context);
            context.addMessage(uic.getClientId(), fm);
        }
    }

    /**
     * Validator-Methode
     *
     * @param fc
     * @param uic
     * @param obj
     * @throws ValidatorException
     */
    public void phoneValidator(
            FacesContext fc,
            UIComponent uic,
            Object obj)
            throws ValidatorException {
        String regexPhone = "^[0-9]{0,4}\\s?[0-9]{5,7}\\s?$";
        String phoneString = obj.toString();
        FacesMessage fm;
        boolean matches = matches(regexPhone, phoneString);

        if (!matches) {
            //if(!Pattern.matches(regexPhone,phoneString)){
            fm = new FacesMessage("Hinweis: Nummer überprüfen!");
            throw new ValidatorException(fm);
        } else {
            fm = new FacesMessage("Nummer formal korrekt!");
            FacesContext.getCurrentInstance().addMessage(uic.getClientId(), fm);
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
     * Get the value of not_registered
     *
     * @return the value of not_registered
     */
    public boolean isRegistered() {
        return not_registered;
    }

    /**
     * Set the value of not_registered
     *
     * @param registered new value of not_registered
     */
    public void setRegistered(boolean registered) {
        this.not_registered = registered;
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

    /**
     *
     * @return
     */
    public String getCphone() {
        return cphone;
    }

    /**
     *
     * @param cphone
     */
    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    /**
     *
     * @param birthdate
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFedState() {
        return fedState;
    }

    public void setFedState(String fedState) {
        this.fedState = fedState;
    }

    public Map<String, String> getSupportedCountries() {
        return supportedCountries;
    }

    public Map<String, String> getSupportedFedStates() {
        return supportedFedStates;
    }

    /**
     * initialize hashmaps for selectOne with values for greeting, country and
     * fedstate
     *
     */
    public void init_maps() {

        // init Greetings
        greetings.put("Hr.", "Hr.");
        greetings.put("Fr.", "Fr.");
        setGreeting(greetings.get("Fr."));

        // init possible countries
        supportedCountries.put("DE", "DE");
        supportedCountries.put("DK", "DK");
        supportedCountries.put("NL", "NL");
        supportedCountries.put("AU", "AU");
        supportedCountries.put("CH", "CH");
        setCountry(supportedCountries.get("DE"));

        // init possible regions
        supportedFedStates.put("nrw", "nrw");
        supportedFedStates.put("ns", "ns");
        supportedFedStates.put("he", "he");
        supportedFedStates.put("by", "by");
        supportedFedStates.put("bw", "bw");
        supportedFedStates.put("rp", "rp");
        supportedFedStates.put("sh", "sh");
        supportedFedStates.put("sa", "sa");
        supportedFedStates.put("th", "th");
        supportedFedStates.put("br", "br");
        supportedFedStates.put("hb", "hb");
        supportedFedStates.put("sl", "sl");
        supportedFedStates.put("mvp", "mvp");
        supportedFedStates.put("bin", "bin");
        setFedState(supportedFedStates.get("nrw"));

    }

    /**
     * if any attribute that is optional is left empty, it will be filled with a
     * default value that fits the database requirements
     */
    public void setDefaultForEmptyAttributes() {
        if (this.email.isEmpty()) {
            this.email = DEFAULTEMAIL;
        }

        if (this.cphone.isEmpty()) {
            this.cphone = DEFAULTCPHONE;
        }

        if (this.street.isEmpty()) {
            this.street = DEFAULTSTREET;
        }

        if (this.city.isEmpty()) {
            this.city = DEFAULTCITY;
        }

        if (this.citycode.isEmpty()) {
            this.citycode = DEFAULTCITYCODE;
        }
    }

}
