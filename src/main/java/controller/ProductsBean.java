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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import model.ProductListItem;
import org.primefaces.event.SelectEvent;
import util.DataBean;

/**
 * Name: ProductBean Aufgabe: Klasse für interaktion mit Produktwebsite Version:
 * 1.0 Letzte Änderung: 01.05.2022 Realisierung Markus Hartlage
 */
@Named(value = "productsBean")
@SessionScoped
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

    @Inject
    private LoginBean lb;
    
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

        for (Product p : db.getProductObjectList()) {
            ProductListItem pi = new ProductListItem(p, 1);
            productListItems.add(pi);
        }
        not_in_Products = false;
        products = db.getProductObjectList();
        LOGGER.log(Level.INFO, "init");

        // selectedProductObject = new Product();
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
        ProductListItem selectedProductListItemObject = (ProductListItem) ev.getObject();

        //this.selectedProductObject = (Product) ev.getObject();
        Product p = selectedProductListItemObject.getProduct();

        // copy product into selected Porduct Object
        this.selectedProductObject = p;
        LOGGER.log(Level.INFO, "setter");
    }

    public Product getSelectedProductObject() {
        return selectedProductObject;
    }

    public void setSelectedProductObject(Product selectedProductObject) {
        this.selectedProductObject = selectedProductObject;
    }

    public void editProduct() {
        LOGGER.log(Level.INFO, "edit");

        LOGGER.log(Level.INFO, "selected p id was: {0}, {1}, {2}", new Object[]{selectedProductObject.getPrid(), selectedProductObject.getPrname(), selectedProductObject.getPramount()});

        FacesMessage fm = new FacesMessage();

        if (db.updateProduct(this.selectedProductObject)) {
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            fm.setSummary("Product was updated!");
        } else {
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
            fm.setSummary("Product was not updated!");
        }

        // update product list
        for (ProductListItem pi : productListItems) {
            if (pi.getProduct().getPrid().equals(selectedProductObject.getPrid())) {
                pi.setProduct(selectedProductObject);
            }
        }

        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public String addProduct() {
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
            return null;
        } else {

            fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erfolg",
                    ": Produkt hinzufügen erfolgreich!");
            context.addMessage(null, fm);
            return "products.xhtml";
        }
    }

    public void deleteProduct() {

    }

    public boolean renderProductEditDialog(){
        return lb.isIsAdmin() && lb.isLoggedIn();
    }
}
