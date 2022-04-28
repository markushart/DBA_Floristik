/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ShoppingCartItem;

/**
 *
 * @author marku
 */
@Named(value = "shoppingCartBean")
@SessionScoped
public class ShoppingCartBean implements Serializable {

    private static final Logger LOGGER = 
            Logger.getLogger(ShoppingCartBean.class.getName());
    private ArrayList<ShoppingCartItem> items;
    private float overallPrice;
    private FacesContext context;

    private ShoppingCartItem lastAddedItem;

    @PostConstruct
    public void init() {

        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Products: Session ID: {0}", session.getId());

        items = new ArrayList<>();
    }

    /**
     * Creates a new instance of ShoppingCartBean
     */
    public ShoppingCartBean() {
    }

    public String order(){
        if (true) {
            return "login.xhtml";
        } else {
            return "pay.xhtml";
        }

    }
    
    /**
     * adds a shoppingCartItem with number n and product p, 
     * if product with the same Id is
     * allready in Cart, add n to its ShoppingCartItem number
     *
     * @param n number of Product
     * @param p the product in the shopping Cart
     */
    public void addItem(int n, Product p) {
        ShoppingCartItem shi = new ShoppingCartItem();
        shi.setNumber(n);
        shi.setProduct(p);
        boolean isInCart = false;
        for (ShoppingCartItem i : items) {
            if (i.getProduct().getId() == p.getId()) {
                isInCart = true;
                i.setNumber(i.getNumber() + n);
                break;
            }
        }
        if (!isInCart) {
            this.items.add(shi);
        }
        setOverallPrice();
    }

    public void addItem(ShoppingCartItem shi) {
        addItem(shi.getNumber(), shi.getProduct());
    }

    public void removeItem(int i) {
        this.items.remove(i);
        setOverallPrice();
    }

    public void removeItem(ShoppingCartItem item) {
        this.items.remove(item);
        setOverallPrice();
    }

    /**
     * Get the value of lastAddedItem
     *
     * @return the value of lastAddedItem
     */
    public ShoppingCartItem getLastAddedItem() {
        return lastAddedItem;
    }

    /**
     * Set the value of lastAddedItem
     *
     * @param lastAddedItem new value of lastAddedItem
     */
    public void setLastAddedItem(ShoppingCartItem lastAddedItem) {
        addItem(lastAddedItem);
        this.lastAddedItem = lastAddedItem;
    }

    /**
     *
     * @param ev
     */
    public void spinnerAjaxListener(AjaxBehaviorEvent ev) {
        for (ShoppingCartItem i : items) {
            i.getWholePrice();
        }
    }

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public ArrayList<ShoppingCartItem> getItems() {
        return items;
    }

    /**
     * Set the value of items
     *
     * @param items new value of items
     */
    public void setItems(ArrayList<ShoppingCartItem> items) {
        this.items = items;
    }

    /**
     * Get the value of overallPrice
     *
     * @return the value of overallPrice
     */
    public float getOverallPrice() {
        return overallPrice;
    }

    /**
     *
     */
    public void setOverallPrice() {
        float sum = 0;
        for (ShoppingCartItem i : items) {
            i.setWholePrice();
            sum += i.getWholePrice();
        }
        this.overallPrice = sum;
    }
}
