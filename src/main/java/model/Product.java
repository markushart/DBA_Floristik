/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import java.io.Serializable;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author marku
 */
public class Product implements Serializable {

    private String name;
    private int id;
    private float price;
    private DefaultStreamedContent image;

    /**
     * Creates a new instance of product
     */
    public Product() {
        this("", 0, 0.0f);
    }

    public Product(String name, int id, float price) {
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
