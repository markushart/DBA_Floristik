/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Service;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.ServiceListItem;
import org.primefaces.event.SelectEvent;
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
            = Logger.getLogger(ServicesBean.class.getName());

    private ArrayList<ServiceListItem> serviceListItems;

    @Inject
    private DataBean db;
    
    @Inject
    private Service selectedServiceObject;

    /**
     * Creates a new instance of ServicesBean
     */
    public ServicesBean() {
    }

    @PostConstruct
    public void init() {

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Services: Session ID: {0}", session.getId());

        serviceListItems = new ArrayList<>();
        
        ArrayList<Service> services = db.getServiceList();
        
        // move list of services to serviceilistitem list
        for (Service s : services){
            ServiceListItem item = new ServiceListItem(s);
            serviceListItems.add(item);
        }
        selectedServiceObject = new Service();
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
     * Get the value of serviceListItems
     *
     * @return the value of serviceListItems
     */
    public ArrayList<ServiceListItem> getServiceListItems() {
        return serviceListItems;
    }

    /**
     * Set the value of serviceListItems
     *
     * @param services new value of serviceListItems
     */
    public void setServiceListItems(ArrayList<ServiceListItem> services) {
        this.serviceListItems = services;
    }
    
    /**
     * Ajax-listener="#{ServiceBean.selectService}" in displayService
     *
     * @param ev
     */
    public void selectService(SelectEvent ev) {
        ServiceListItem selectedServiceListItemObject =(ServiceListItem) ev.getObject();
        
        //this.selectedProductObject = (Product) ev.getObject();
        
        this.selectedServiceObject =selectedServiceListItemObject.getService();
    }
    
    public Service getSelectedServiceObject() {
        return selectedServiceObject;
    }

    public void setSelectedServiceObject(Service selectedServiceObject) {
        this.selectedServiceObject = selectedServiceObject;
    }

}
