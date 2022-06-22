/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Product;
import com.dba_floristik.Productcategory;
import com.dba_floristik.Supplier;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
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

    private List<Product> products;
    
    @NotNull
    private String prname;
    private int pramount;
    private float ppricenetto;
    
    private boolean not_in_Products;
    private FacesContext context;
    
    /**
     * Creates a new instance of ProductsBean
     */
    public ProductsBean() {
    }

    @PostConstruct
    public void init() {

        //HttpSession session
        //        = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        //LOGGER.log(Level.INFO, "Products: Session ID: {0}", session.getId());

        productListItems = new ArrayList<>();
        
        for(Product p : db.getProductObjectList()){
            ProductListItem pi = new ProductListItem(p, 1);
            productListItems.add(pi);
        }
        not_in_Products = false;
        selectedProductObject = new Product();
        products = db.getProductObjectList();
        
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

    public DataBean getDb() {
        return db;
    }

    public void setDb(DataBean db) {
        this.db = db;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPrname() {
        return prname;
    }

    public void setPrname(String prname) {
        this.prname = prname;
    }

    public int getPramount() {
        return pramount;
    }

    public void setPramount(int pramount) {
        this.pramount = pramount;
    }

    public float getPpricenetto() {
        return ppricenetto;
    }

    public void setPpricenetto(float ppricenetto) {
        this.ppricenetto = ppricenetto;
    }

    public boolean isNot_in_Products() {
        return not_in_Products;
    }

    public void setNot_in_Products(boolean not_in_Products) {
        this.not_in_Products = not_in_Products;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    /**
     * Ajax-listener="#{productBean.selectProduct}" in displayProduct
     *
     * @param ev
     */
    public void selectProduct(SelectEvent ev) {
        ProductListItem selectedProductListItemObject =(ProductListItem) ev.getObject();
        
        //this.selectedProductObject = (Product) ev.getObject();
        
        this.selectedProductObject =selectedProductListItemObject.getProduct();
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
    
    public String addProduct(){
        FacesMessage fm;
        not_in_Products = true;
        if (products == null) {
            LOGGER.log(Level.WARNING, "Productlist was null!");
            return null;
        }
        System.out.println("''''''''''HIERÄÄÄÄÄ");
        try {
            if (not_in_Products) {
                // IDs werden automatisch vergeben
                Product p = new Product(0, this.prname, this.pramount, this.ppricenetto);
                
                LOGGER.log(Level.INFO, "Prid: {0}", p.getPrid());
                
                //Adress add = new Adress(c.getCid(), this.street, this.city, this.fedState, this.citycode, this.country, new Date());
//                Collection<Adress> add_coll = new ArrayList<>();
//                add_coll.add(add);
                Productcategory PcatDummy = new Productcategory(0, "Supplierdummy");
                Supplier SupplierDummy = new Supplier(0, "SupplierDummy", "SupplierDummy",
                        "SupplierDummy", "SupplierDummy", "SupplierDummy");
                not_in_Products = this.db.persistProduct(p, PcatDummy, SupplierDummy);
//                c.setAdressCollection(add_coll);
            }
        } catch (ConstraintViolationException ex) {
            // 
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Eine der Eingaben entsprach nicht den Eingabebeschränkungen.");
            context.addMessage(null, fm);
            return null;
        }
         if (!not_in_Products) {
            fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehlschlag",
                    ": Fehlschlag beim Produkt hinzufügen.");
            context.addMessage(null, fm);
            return null;}
        else {

            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg",
                    ": Produkt hinzufügen erfolgreich!");
            context.addMessage(null, fm);
            return "products.xhtml";
        }
    }
    public void deleteProduct(){
        
    }

}
