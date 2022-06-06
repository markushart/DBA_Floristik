/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Name: Service Aufgabe: Klasse für das Objekt Service bzw. für
 * Dienstleistungen Version: 1.0 Letzte Änderung: 01.05.2022 Realisierung Markus
 * Hartlage
 */
public class Service_old extends Buyable implements Serializable {

    private Date serviceDate;

    public Service_old() {
        super();
        this.serviceDate = new Date(System.currentTimeMillis());
    }

    public Service_old(String name, int id, float price) {
        super(name, id, price);
        this.serviceDate = new Date(System.currentTimeMillis());
    }

    public Service_old(String name, int id, float price, Date serviceDate) {
        super(name, id, price);
        this.serviceDate = serviceDate;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

}
