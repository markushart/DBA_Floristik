/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;

/**
 * Name:            Buyable
 * Aufgabe:         Superklasse für Produkte und Services
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Markus Hartlage
 */
public class Buyable implements Serializable {

    protected String name;
    protected int id;
    protected float price;
    protected DefaultStreamedContent image;

    /**
     * Creates a new instance of product
     */
    public Buyable() {
        this("", 0, 0.0f);
    }

    public Buyable(String name, int id, float price) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = null;
    }

    /**
     * Get the value of price
     *
     * @return the value of price
     */
    public float getPrice() {
        return price;
    }

    /**
     * Set the value of price
     *
     * @param price new value of price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return an image of the product
     */
    public DefaultStreamedContent getImage() {
        return image;
    }

    /**
     * Set an image of the product
     *
     * @param image new value of image
     */
    public void setImage(DefaultStreamedContent image) {
        this.image = image;
    }
}
