/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.Service_old;
import util.DataBean;

/**
 * Name: ServiceBean 
 Aufgabe: Klasse für INteraktion mit Service_old Seite 
 Version: 1.0 
 Letzte Änderung: 01.05.2022 
 Realisierung Markus Hartlage
 */
@Named(value = "servicesBean")
@RequestScoped
public class ServicesBean {

    private static final Logger LOGGER
            = Logger.getLogger(ProductsBean.class.getName());

    private ArrayList<Service_old> services;

    @Inject
    private DataBean db;

    /**
     * Creates a new instance of ServicesBean
     */
    public ServicesBean() {
    }

    @PostConstruct
    public void init() {

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Products: Session ID: {0}", session.getId());

        db = new DataBean();
        services = new ArrayList<>();
        db.generateTestServices();
        this.services = db.getServiceList();
    }

    /**
     * Get the value of db
     *
     * @return the value of db
     */
    public DataBean getDb() {
        return db;
    }

    /**
     * Set the value of db
     *
     * @param db new value of db
     */
    public void setDb(DataBean db) {
        this.db = db;
    }

    /**
     * Get the value of services
     *
     * @return the value of services
     */
    public ArrayList<Service_old> getServices() {
        return services;
    }

    /**
     * Set the value of services
     *
     * @param services new value of services
     */
    public void setServices(ArrayList<Service_old> services) {
        this.services = services;
    }

}
