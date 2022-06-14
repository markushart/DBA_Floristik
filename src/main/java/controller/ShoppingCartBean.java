/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.dba_floristik.Product;
import com.dba_floristik.Service;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import model.ProductListItem;
import model.ServiceListItem;
import util.DataBean;

/**
 * Name: ShoppingCartBean Aufgabe: Klasse für Interaktion mit dem Warenkorb
 * Version: 1.0 Letzte Änderung: 01.05.2022 Realisierung Markus Hartlage
 */
@Named(value = "shoppingCartBean")
@SessionScoped
public class ShoppingCartBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ShoppingCartBean.class.getName());
    // the latest time customer can get an Order
    private static final int INITIALDELIVERYHOUR = 8;
    // customers can only order MAXORDERPERIOD days ahead from now
    private static final int MAXORDERPERIOD = 21;
    private ArrayList<ProductListItem> productListItems;
    private ArrayList<ServiceListItem> serviceListItems;
    private float overallPrice;
    private FacesContext context;
    private LocalDate deliveryDate;
    private Map<String, LocalTime> deliveryTimeOptions;
    private LocalTime deliveryTime;
    private ProductListItem lastAddedProductItem;
    private ServiceListItem lastAddedServiceItem;
    private LocalDate minDate;
    private LocalDate maxDate;

    @Inject
    private LoginBean lbean;

    @Inject
    private DataBean db;

    @PostConstruct
    public void init() {
        deliveryTimeOptions = new HashMap<>();
        for (int i = 0; i < 12; ++i) {
            LocalTime time = LocalTime.of(INITIALDELIVERYHOUR + i, (i % 2) * 30, 0);
            deliveryTimeOptions.put(time.format(DateTimeFormatter.ofPattern("HH:mm")), time);
        }

        deliveryTime = LocalTime.of(INITIALDELIVERYHOUR, 0, 0);

        context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Products: Session ID: {0}", session.getId());

        productListItems = new ArrayList<>();
        serviceListItems = new ArrayList<>();

        minDate = LocalDate.now().plusDays(1);
        deliveryDate = minDate;
        maxDate = LocalDate.now().plusDays(MAXORDERPERIOD);
    }

    /**
     * Creates a new instance of ShoppingCartBean
     */
    public ShoppingCartBean() {
    }

    /**
     * initiates order process and sends a message what went wrong / success
     */
    public void order() {
        context = FacesContext.getCurrentInstance();
        FacesMessage fm;
        if (lbean != null) {
            if (lbean.isLoggedIn()) {
                if (productListItems.isEmpty() && serviceListItems.isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Einkaufswagen leer!", "");
                } else if (!validateDate()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Unzulässiger Liefertag", "Bitte innerhalb der nächsten " + MAXORDERPERIOD + " Tage bestellen.");
                } else {
                    
                    // persist the users order to the database
                    db.persistShoppingCart(this.productListItems, this.serviceListItems);
                    
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Bestellung abgeschlossen.", "Vielen Dank für ihre Bestellung.");
                    clearCart();
                }
            } else {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Nicht eingeloggt", ": bitte zunächst einloggen!");
            }
        } else {
            fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Nicht eingeloggt", ": bitte zunächst einloggen!");
        }
        LOGGER.log(Level.INFO, fm.getDetail());
        context.addMessage("orderForm:orderButton", fm);
    }

    public float wholePrice(int amount, float price) {
        float wholePrice = amount * price;
        return wholePrice;
    }

    /**
     * adds a product p, if product with the same Id is allready in Cart, add n
     * to its number
     *
     * @param p the product in the shopping Cart
     */
    public void addProductListItem(ProductListItem p) {
        boolean isInCart = false;
        for (ProductListItem i : productListItems) {
            if (Objects.equals(i.getProduct().getPrid(), p.getProduct().getPrid())) {
                isInCart = true;
                i.setOrderAmount(i.getOrderAmount() + p.getOrderAmount());
                break;
            }
        }
        if (!isInCart) {
            this.productListItems.add(p);
        }
        setOverallPrice();
    }

    /**
     * add a service to the serviceListItems list
     *
     * @param s the service to add
     */
    public void addService(ServiceListItem s) {
        this.serviceListItems.add(s);
        setOverallPrice();
    }

    /**
     *
     * @param i the index of product to remove
     */
    public void removeProduct(int i) {
        try {
            this.productListItems.remove(i);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "found no product {0} to remove!", i);
        }
        setOverallPrice();
    }

    /**
     *
     * @param p product to remove
     */
    public void removeProduct(ProductListItem p) {
        this.productListItems.remove(p);
        setOverallPrice();
    }

    /**
     *
     * @param i index of service to remove
     */
    public void removeService(int i) {
        try {
            this.serviceListItems.remove(i);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "found no service {0} to remove!", i);
        }
        setOverallPrice();
    }

    public void removeService(ServiceListItem s) {
        this.serviceListItems.remove(s);
        setOverallPrice();
    }

    /**
     * remove erything from the shoppingCart
     */
    public void clearCart() {
        productListItems.clear();
        serviceListItems.clear();
        this.overallPrice = 0;
    }

    /**
     *
     * @return true if date is between minDate and maxDate, else false
     */
    public boolean validateDate() {
        LOGGER.log(Level.INFO, " {0} {1} {2}", new Object[]{minDate, deliveryDate, maxDate});
        if (deliveryDate.isBefore(minDate) || deliveryDate.isAfter(maxDate)) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ausserhalb der Lieferzeiten",
                    ": LieferZeitpunkt muss innerhalb der nächsten " + MAXORDERPERIOD + " Tage liegen.");
            context = FacesContext.getCurrentInstance();
            context.addMessage(null, fm);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the value of lastAddedProductItem
     *
     * @return the value of lastAddedProductItem
     */
    public ProductListItem getLastAddedProduct() {
        return lastAddedProductItem;
    }

    /**
     * Set the value of lastAddedProductItem
     *
     * @param item
     */
    public void setLastAddedItem(ProductListItem item) {
        addProductListItem(item);
        this.lastAddedProductItem = item;
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
     * @param products new value of productListItems
     */
    public void setProducts(ArrayList<ProductListItem> products) {
        this.productListItems = products;
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
     * return price of whole shopping cart
     * 
     */
    public void setOverallPrice() {
        float sum = 0;
        for (ProductListItem p : productListItems) {
            
            sum += p.getPriceForAmount();
        }
        for (ServiceListItem s : serviceListItems) {
            sum += s.getService().getServprice();
        }
        this.overallPrice = sum;
    }

    /**
     * Get the value of lbean
     *
     * @return the value of lbean
     */
    public LoginBean getLbean() {
        return lbean;
    }

    /**
     * Set the value of lbean
     *
     * @param lbean new value of lbean
     */
    public void setLbean(LoginBean lbean) {
        this.lbean = lbean;
    }

    /**
     *
     * @return serviceListItems in the shopping cart
     */
    public ArrayList<ServiceListItem> getServiceListItems() {
        return serviceListItems;
    }

    /**
     *
     * @param services to put in the shopping cart
     */
    public void setServiceListItems(ArrayList<ServiceListItem> services) {
        this.serviceListItems = services;
    }

    /**
     *
     * @return
     */
    public Map<String, LocalTime> getDeliveryTimeOptions() {
        return deliveryTimeOptions;
    }

    /**
     * Get the value of deliveryTime
     *
     * @return the value of deliveryTime
     */
    public LocalTime getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Set the value of deliveryTime
     *
     * @param deliveryTime new value of deliveryTime
     */
    public void setDeliveryTime(LocalTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * Get the value of deliveryDate
     *
     * @return the value of deliveryDate
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * Set the value of deliveryDate
     *
     * @param deliveryDate new value of deliveryDate
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = dateToLocalDate(deliveryDate);
    }

    /**
     *
     * @param local
     * @return
     */
    public Date localDateToDate(LocalDate local) {
        return new Date(local.getYear(), local.getMonthValue(), local.getDayOfMonth());
    }

    /**
     *
     * @param date
     * @return
     */
    public LocalDate dateToLocalDate(Date date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
    }

    /**
     * Get the value of minDate
     *
     * @return the value of minDate
     */
    public Date getMinDate() {
        return localDateToDate(minDate);
    }

    /**
     * Set the value of minDate
     *
     * @param minDate new value of minDate
     */
    public void setMinDate(Date minDate) {
        this.minDate = dateToLocalDate(minDate);
    }

    /**
     * Get the value of maxDate
     *
     * @return the value of maxDate
     */
    public Date getMaxDate() {
        return localDateToDate(maxDate);
    }

    /**
     * Set the value of maxDate
     *
     * @param maxDate new value of maxDate
     */
    public void setMaxDate(Date maxDate) {
        this.maxDate = dateToLocalDate(maxDate);
    }

    public ServiceListItem getLastAddedService() {
        return lastAddedServiceItem;
    }

    public void setLastAddedService(ServiceListItem lastAddedService) {
        this.addService(lastAddedService);
        this.lastAddedServiceItem = lastAddedService;
    }

}
