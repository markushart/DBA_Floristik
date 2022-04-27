/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package model;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author marku
 */
@Named(value = "shoppingCardItem")
@Dependent
public class ShoppingCartItem implements Serializable {

    @Inject private ProductBean product;
    private int number;
    private float wholePrice;
    
    @PostConstruct
    public void init(){
        this.product = null;
        this.number=0;
        wholePrice=0.0f;
    }

    /**
     * Creates a new instance of ShoppingCardItem
     */
    public ShoppingCartItem() {
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
    public ProductBean getProduct() {
        return product;
    }

    /**
     * Set the value of product
     *
     * @param product new value of product
     */
    public void setProduct(ProductBean product) {
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
