/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ShoppingCartItem;

/**
 *
 * @author marku
 */
@Named(value = "shoppingCartBean")
@RequestScoped
public class ShoppingCartBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ShoppingCartBean.class.getName());
    private FacesContext context;

    private ShoppingCartItem lastAddedItem;
    private ArrayList<ShoppingCartItem> items;
    private float overallPrice;

    @Inject
    private UserBean ubean;

    @PostConstruct
    public void init() {

        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "ShoppingCart: Session ID: {0}", session.getId());

        items = new ArrayList<>();
        // init ubean somehow!
        // setItems();
    }

    /**
     * Creates a new instance of ShoppingCartBean
     */
    public ShoppingCartBean() {
    }

    /**
     *
     * @return the next side where the user can either log in or pay
     */
    public String order() {
        if (ubean.getUser().isLoggedIn()) {
            return "pay.xhtml";
        } else {
            return "login.xhtml";
        }

    }

     /**
     * @param n number of Product
     * @param p the product in the shopping Cart
     */
    public void addItem(int n, Product p) {
        ShoppingCartItem shi = new ShoppingCartItem(p, n);
        addItem(shi);
    }
    
    /**
     * adds a shoppingCartItem with number n and product p
     *
     * @param shi ShoppingCartItem
     */
    public void addItem(ShoppingCartItem shi) {
        ubean.getUser().putInCart(shi);
        setOverallPrice();
    }

    public void removeItem(int i) {
        this.ubean.getUser().removeFromCart(i);
        setOverallPrice();
    }

    public void removeItem(ShoppingCartItem item) {
        this.ubean.getUser().removeFromCart(item);
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
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zu Einkaufskorb hinzugefügt", ": " + lastAddedItem.getNumber() 
                + " x " + lastAddedItem.getProduct().getName());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        
        this.lastAddedItem = lastAddedItem;
    }

    /**
     *
     * @param ev
     */
    public void spinnerAjaxListener(AjaxBehaviorEvent ev) {
        for (ShoppingCartItem i : getItems()) {
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
     * Set the value of items with the shopping cart of user
     *
     */
    public void setItems() {
        this.items = ubean.getUser().getShoppingCart();
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
        for (ShoppingCartItem i : getItems()) {
            i.setWholePrice();
            sum += i.getWholePrice();
        }
        this.overallPrice = sum;
    }

    /**
     * Get the value of ubean
     *
     * @return the value of ubean
     */
    public UserBean getUbean() {
        return ubean;
    }

    /**
     * Set the value of ubean
     *
     * @param ubean new value of ubean
     */
    public void setUbean(UserBean ubean) {
        LOGGER.info("ubean was added");
        this.ubean = ubean;
    }
}
