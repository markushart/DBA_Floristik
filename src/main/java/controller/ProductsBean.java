/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Product;
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
import model.ProductListItem;
import util.DataBean;

/**
 * Name:            ProductBean
 * Aufgabe:         Klasse für interaktion mit Produktwebsite
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Markus Hartlage
 */
@Named(value = "productsBean")
@RequestScoped
public class ProductsBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ProductsBean.class.getName());

    // private ArrayList<Product> products; // hier vielleicht einfach eine List mit Orderdetailproduct, diese kann dann auch Menge und Gesamtpreis abbilden
    
    private ArrayList<ProductListItem> productListItems;
    
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

        productListItems = new ArrayList<>();
        ArrayList<Product> products;
        products = db.getProductList();
        
        for(Product p : products){
            ProductListItem pi = new ProductListItem(p, 1);
            productListItems.add(pi);
        }
    }

    /**
     *
     * @param ev
     */
    public void spinnerAjaxListener(AjaxBehaviorEvent ev) {
        for (ProductListItem p : productListItems) {
            //p.setWholePrice();
        }
    }

    /**
     * Get the value of items
     *
     * @return the value of items
     */
    public ArrayList<ProductListItem> getProductListItems() {
        return productListItems;
    }

    /**
     * Set the value of items
     *
     * @param products new value of products
     */
    public void setProducts(ArrayList<ProductListItem> products) {
        this.productListItems = products;
    }

    public ShoppingCartBean getCartBean() {
        return cartBean;
    }

    public void setCartBean(ShoppingCartBean cartBean) {
        this.cartBean = cartBean;
    }

}
