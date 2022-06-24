/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.dba_floristik.Service;
import java.io.Serializable;
import java.util.Date;



/**
 * Name:            ServiceListItem
 * Aufgabe:         Vereinfachung des Datenaustausch mit ShoppingCart
 * Version:         2.0
 * Letzte Änderung: 24.06.2022
 * Realisierung     Markus Hartlage und Sascha Nickel
 */
public class ServiceListItem implements Serializable {

    private Service service;
    private Date serviceDate;

    public ServiceListItem(Service service) {
        this.service = service;
        this.serviceDate = new Date();
    }

    public ServiceListItem(Service service, Date serviceDate) {
        this.service = service;
        this.serviceDate = serviceDate;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

}
