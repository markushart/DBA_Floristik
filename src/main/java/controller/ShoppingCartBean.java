/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
@SessionScoped
public class ShoppingCartBean implements Serializable {

    private static final Logger LOGGER
            = Logger.getLogger(ShoppingCartBean.class.getName());
    // the latest time customer can get an Order
    private static final int INITIALDELIVERYHOUR = 8;
    // customers can only order MAXORDERPERIOD days ahead from now
    private static final int MAXORDERPERIOD = 21;
    private ArrayList<ShoppingCartItem> items;
    private float overallPrice;
    private FacesContext context;
    private LocalDate deliveryDate;
    private Map<String, LocalTime> deliveryTimeOptions;
    private LocalTime deliveryTime;
    private ShoppingCartItem lastAddedItem;

    private LocalDate minDate;

    private LocalDate maxDate;

    @Inject
    private LoginBean lbean;

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

        items = new ArrayList<>();
        minDate = LocalDate.now().plusDays(1);
        deliveryDate = minDate;
        maxDate = LocalDate.now().plusDays(MAXORDERPERIOD);
    }

    /**
     * Creates a new instance of ShoppingCartBean
     */
    public ShoppingCartBean() {
    }

    public void order() {
        context = FacesContext.getCurrentInstance();
        FacesMessage fm;
        if (lbean != null) {
            if (lbean.isLoggedIn()) {
                if (items.isEmpty()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Einkaufswagen leer!", "");
                } else if (!validateDate()) {
                    fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Unzulässiger Liefertag", "Bitte innerhalb der nächsten " + MAXORDERPERIOD + " Tage bestellen.");
                } else {
                    fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Bestellung abgeschlossen.", "Vielen Dank für ihre Bestellung.");
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
        clearCart();
    }

    /**
     * adds a shoppingCartItem with number n and product p, if product with the
     * same Id is allready in Cart, add n to its ShoppingCartItem number
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
        try {
            this.items.remove(i);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "found no item {0} to remove!", i);
        }
        setOverallPrice();
    }

    public void removeItem(ShoppingCartItem item) {
        this.items.remove(item);
        setOverallPrice();
    }

    /**
     * remove erything from the shoppingCart
     */
    public void clearCart() {
        items.clear();
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
}
