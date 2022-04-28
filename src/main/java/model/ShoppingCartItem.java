/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author marku
 */
public class ShoppingCartItem implements Serializable {

    private Product product;
    private int number;
    private float wholePrice;

    /**
     * Creates a new instance of ShoppingCardItem
     */
    public ShoppingCartItem() {
        this(new Product(), 0);
    }

    public ShoppingCartItem(Product product, int number) {
        this.product = product;
        this.number = number;
        if (product == null) {
            wholePrice = 0.0f;
        } else {
            wholePrice=product.getPrice() * number;
        }
    }

    
    
    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Set the value of number
     *
     * @param number new value of number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get the value of product
     *
     * @return the value of product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the value of product
     *
     * @param product new value of product
     */
    public void setProduct(Product product) {
        this.product = product;
    }
    
    /**
     * 
     */
    public void setWholePrice(){
        this.wholePrice = product.getPrice() * number;
    }
    
    /**
     * 
     * @return price of single products times the occurence in shopping card
     */
    public float getWholePrice(){
        return this.wholePrice;
    }
    
}
