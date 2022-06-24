/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 * Name:            User
 * Aufgabe:         Klasse für das Objekt User
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
 */
public class User {
    
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    
    private Integer id;
    
    private String firstName;

    private String lastName;

    private String email;
    
    private String username;

    private int password;

    private String phone;

    private Date birthdate;


    private UserRole role = UserRole.NOBODY;
    
    private String salutation;
    
    /**
     * Creates a new instance of user
     */
    public User() {
        this("","","","",0,"");
    }
    
    /**
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param username
     * @param password
     * @param salutation
     * @param role 
     */
    public User(String firstName, String lastName, String email, String username, int password, String salutation, UserRole role) {
        this(firstName, lastName, email, username, password, salutation);
        this.role = role;
    }
    
    public User(String firstName, String lastName, String email, String username, int password, String salutation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salutation = salutation;
    }
    
    public User(Integer id, String firstName, String lastName, String salutation, String email, String phone ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salutation = salutation;
        this.email = email;
        this.phone = phone;
    }
    
       public User(int id) {
        this.id = id;
    }


    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Get the value of role
     *
     * @return the value of role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Set the value of role
     *
     * @param role new value of role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Get the value of salutation
     *
     * @return the value of salString depending on salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * Set the value of salutation
     *
     * @param salutation new value of salutation
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * Get the value of password
     *
     * @return the value of password
     */
    public int getPassword() {
        return password;
    }

    /**
     * Set the value of password
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password.hashCode();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
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
     * Get the value of lastName
     *
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the value of lastName
     *
     * @param lastName new value of lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
        /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the value of username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    public boolean login(String password){
        return this.password == password.hashCode();
           
    }
    
    /**
     * 
     * @return tell if the user is an admin or not
     */
    public boolean isAdmin(){
        LOGGER.log(Level.INFO, "Die Rolle von {0} ist {1}", new Object[]{username, role});
        return this.role == UserRole.ADMIN;
    }
}
