/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Product;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.ProductListItem;
import org.primefaces.event.SelectEvent;
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

    
    private ArrayList<ProductListItem> productListItems;
    
    @Inject
    private DataBean db;

    @Inject
    private ShoppingCartBean cartBean;
    
    @Inject
    private Product selectedProductObject;

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
        
        for(Product p : db.getProductObjectList()){
            ProductListItem pi = new ProductListItem(p, 1);
            productListItems.add(pi);
        }
        
        selectedProductObject = new Product();
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

    public Product getSelectedProductObject() {
        return selectedProductObject;
    }

    public void setSelectedProductObject(Product selectedProductObject) {
        this.selectedProductObject = selectedProductObject;
    }
    
    public void editProduct(){
        int index = 0;
        for(Product p : db.getProductObjectList()){
            if (Objects.equals(p.getPrid(), selectedProductObject.getPrid())){
                db.getProductObjectList().set(index, this.selectedProductObject);
                break;
            }
            index++;
        }
        index = 0;
        /*
        for (Product p : this.productListItems){
            if (Objects.equals(p.getPrid(), this.selectedProductObject.getPrid())){
              // this.filteredUserObjectList.set(index, this.selectedUserObject); 
            }
            index++;
        }
        */
        FacesMessage fm = new FacesMessage("Editieren beendet!");
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

}
