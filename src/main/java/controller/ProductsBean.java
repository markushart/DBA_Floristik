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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ShoppingCartItem;
import util.DataBean;

/**
 *
 * @author marku
 */
@Named(value = "productsBean")
@RequestScoped
public class ProductsBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ProductsBean.class.getName());

    private ArrayList<ShoppingCartItem> items;

    @Inject
    private DataBean db;

    @Inject
    private ShoppingCartBean cartBean;

    /**
     * Creates a new instance of ProductsBean
     */
    public ProductsBean() {
    }

    @PostConstruct
    public void init() {

        HttpSession session
                = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Products: Session ID: {0}", session.getId());

        db = new DataBean();
        items = new ArrayList<>();
        db.generateTestProducts();
        ArrayList<Product> products = db.getProductList();
        for (Product p : products) {
            items.add(new ShoppingCartItem(p, 1));
            LOGGER.log(Level.INFO, "adding product: {0}", p.getName());
        }
    }

    /**
     *
     * @param ev
     */
    public void spinnerAjaxListener(AjaxBehaviorEvent ev) {
        for (ShoppingCartItem i : items) {
            i.setWholePrice();
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

    public ShoppingCartBean getCartBean() {
        return cartBean;
    }

    public void setCartBean(ShoppingCartBean cartBean) {
        this.cartBean = cartBean;
    }

}
