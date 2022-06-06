/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 * Name:            Product_old
 Aufgabe:         Klasse für Produkte
 Version:         1.0
 Letzte Änderung: 01.05.2022
 Realisierung     Markus Hartlage
 */
public class Product_old extends Buyable implements Serializable {

    int number;
    float wholePrice;
    
    /**
     * 
     */
    public Product_old() {
        super();
        this.number=0;
        this.wholePrice = 0.0f;
    }

    public Product_old(String name, int id, float price, int number) {
        super(name, id, price);
        this.number = number;
        this.wholePrice = number * price;
    }
    
    public Product_old(String name, int id, float price) {
        super(name, id, price);
        this.wholePrice = number * price;
    }

    /**
     * 
     * @return 
     */
    public int getNumber() {
        return number;
    }

    /**
     * 
     * @param number 
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * set whole price to price * number
     */
    public void setWholePrice(){
        this.wholePrice = price * number;
    }
    
    /**
     * 
     * @return price of single products times the number
     */
    public float getWholePrice(){
        return this.wholePrice;
    }

    
    
    


}
