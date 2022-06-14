/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.dba_floristik.Service;
import java.io.Serializable;
import java.util.Date;

/**
 * Name: Service Aufgabe: Klasse für das Objekt Service bzw. für
 * Dienstleistungen Version: 1.0 Letzte Änderung: 01.05.2022 Realisierung Markus
 * Hartlage
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
