/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author marku
 */
@Named(value = "user")
@Dependent
public class User {
    
    private static final String[] salString = {"Herr", "Frau"};
    
    private String firstName = "";

    private String lastName = "";

    private String email = "";
    
    private int password;

    private boolean loggedIn = false;

    private UserRole role;
    
    private boolean salutation;

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
        if (salutation) return salString[0]; else return salString[1];
    }

    /**
     * Set the value of salutation
     *
     * @param salutation new value of salutation
     */
    public void setSalutation(boolean salutation) {
        this.salutation = salutation;
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
     * @param signedIn new value of loggedIn
     */
    public void setLoggedIn(boolean signedIn) {
        this.loggedIn = signedIn;
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
     * Creates a new instance of user
     */
    // public User(String firstName, String lastName, String email, Address address, String password) {
    // }
    
    /**
     * Creates a new instance of user
     */
    public User() {
    }
    
    public void login(String email, String password){
        if (this.password == password.hashCode()) {
            setLoggedIn(true);
        }
    }
}
