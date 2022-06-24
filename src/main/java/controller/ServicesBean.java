/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.ServiceListItem;
import org.primefaces.event.SelectEvent;
import util.DataBean;

/**
 * Name: ServiceBean Aufgabe: Klasse für Interaktion mit Service Seite Version:
 * 2.0 Letzte Änderung: 24.06.2022 Realisierung Markus Hartlage und Sascha
 * Nickel
 */
@Named(value = "servicesBean")
@SessionScoped
public class ServicesBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ServicesBean.class.getName());

    private ArrayList<ServiceListItem> serviceListItems;

    @Inject
    private DataBean db;

    @Inject
    private Service selectedServiceObject;

    @Inject
    private LoginBean lb;

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
        for (Service s : services) {
            ServiceListItem item = new ServiceListItem(s);
            serviceListItems.add(item);
        }
        selectedServiceObject = new Service();
    }

    /**
     * Verändert den ausgewählten Service
     *
     *
     */
    public void editService() {
        LOGGER.log(Level.INFO, "edit Service");

        LOGGER.log(Level.INFO, "selected s id was: {0}, {1}, {2}", new Object[]{selectedServiceObject.getServid(), selectedServiceObject.getServname(), selectedServiceObject.getServprice()});

        FacesMessage fm = new FacesMessage();

        boolean success = db.updateService(selectedServiceObject);

        // update service list
        for (ServiceListItem si : serviceListItems) {
            if (si.getService().getServid().equals(selectedServiceObject.getServid())) {
                si.setService(selectedServiceObject);
            }
        }
        if (success) {
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            fm.setSummary("Service was updated!");
        } else {
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            fm.setSummary("Service was not updated!");
        }
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    /**
     * Löscht das ausgewählte Product
     *
     *
     */
    public void deleteService() {
        LOGGER.log(Level.INFO, "delete");

        LOGGER.log(Level.INFO, "selected s id was: {0}, {1}, {2}", new Object[]{selectedServiceObject.getServid(), selectedServiceObject.getServname(), selectedServiceObject.getServprice()});

        FacesMessage fm = new FacesMessage();

        if (db.removeService(this.selectedServiceObject)) {
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            fm.setSummary("Service was removed!");
        } else {
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            fm.setSummary("Service was not removed!");
        }

        // remove service list
        for (ServiceListItem si : serviceListItems) {
            if (si.getService().getServid().equals(selectedServiceObject.getServid())) {
                si.setService(null);

            }
        }

        FacesContext.getCurrentInstance().addMessage(null, fm);

    }

    public boolean renderServiceEditDialog() {
        
        return lb.isIsAdmin() && lb.isLoggedIn();
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
        ServiceListItem selectedServiceListItemObject = (ServiceListItem) ev.getObject();
        LOGGER.info("############# SELECT SERVICE #############");

        LOGGER.info(selectedServiceListItemObject.getService().getServid().toString());
        LOGGER.info("############# SELECT SERVICE #############");
        //this.selectedProductObject = (Product) ev.getObject();
        this.selectedServiceObject = selectedServiceListItemObject.getService();
    }

    public Service getSelectedServiceObject() {
        return selectedServiceObject;
    }

    public void setSelectedServiceObject(Service selectedServiceObject) {
        this.selectedServiceObject = selectedServiceObject;
    }

}
