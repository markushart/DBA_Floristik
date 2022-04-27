/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author marku
 */
@Named(value = "logoutBean")
@Dependent
public class LogoutBean {

    /**
     * Creates a new instance of LogoutBean
     */
    public LogoutBean() {
    }
    
    public void logout(){
        FacesContext context=FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "logout", "Auf Wiedersehen!");
        context.addMessage(null, fm);
    }
    
}
