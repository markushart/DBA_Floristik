/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package view;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import model.ProductBean;
import model.ShoppingCartItem;

/**
 *
 * @author marku
 */
@Named(value = "shoppingCartView")
@SessionScoped
public class ShoppingCartView implements Serializable {

    private ArrayList<ShoppingCartItem> items;

    @PostConstruct
    public void init(){        
        items = new ArrayList<>();
        ProductBean flieder = new ProductBean();
        flieder.setId(0);
        flieder.setName("flieder");
        flieder.setPrice(0.5f);
        addItem(2, flieder);
        
        ProductBean rose = new ProductBean();
        rose.setId(1);
        rose.setName("rose");
        rose.setPrice(1.5f);
        addItem(5, rose);
        
        ProductBean daisy = new ProductBean();
        daisy.setId(2);
        daisy.setName("Gänseblümchen");
        daisy.setPrice(0.7f);
        addItem(4, daisy);
    }
    
    /**
     * adds a product n times to the Cart, if product with the same Id is allready in Cart,
     * add n to its ShoppingCartItem number
     * @param n number of Product
     * @param p the product in the shopping Cart
     */
    public void addItem(int n, ProductBean p){
        ShoppingCartItem shi = new ShoppingCartItem();
        shi.setNumber(n);
        shi.setProduct(p);
        boolean isInCart = false;
        for(ShoppingCartItem i:items){
            if (i.getProduct().getId() == p.getId()){
                isInCart = true;
                i.setNumber(i.getNumber() + n);
                break;
            }
        }
        if (!isInCart) this.items.add(shi);
    }
    
    public void addItem(ShoppingCartItem shi){
        addItem(shi.getNumber(), shi.getProduct());
    }
    
    /**
     * 
     * @param ev 
     */
    public void spinnerAjaxListener(AjaxBehaviorEvent ev){
        for (ShoppingCartItem i:items) i.getWholePrice();
    }
    
    /**
     * Creates a new instance of DataListView
     */
    public ShoppingCartView() {
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
        float sum = 0;
        for(ShoppingCartItem i:items) sum += i.getWholePrice();
        return sum;
    }
}
