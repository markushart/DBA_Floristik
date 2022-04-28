/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import model.Product;
import model.User;
import model.UserRole;

/**
 *
 * @author marku
 */
@Named(value = "dataBean")
@SessionScoped
public class DataBean implements Serializable {

    static final Logger LOGGER = Logger.getLogger(DataBean.class.getName());
    private ArrayList<User> userList;

    private ArrayList<Product> productList;

    @PostConstruct
    public void init() {

    }

    /**
     * Creates a new instance of DataBean
     */
    public DataBean() {
    }

    public void generateTestUsers() {
        userList = new ArrayList<>();
        userList.add(new User("Markus", "Hartlage", "markus.hartlage@fh-bielefeld.de",
                "hartmark", "Hallo1234".hashCode(), "Herr"));
        userList.add(new User("Bianca", "Beispiel", "biancab@yahoo.com",
                "bibibsp", "GanzGeheim123".hashCode(), "Frau"));
        userList.add(new User("Frank", "Floristiker", "frank@floristik.de",
                "flowerfrank", "L0tusBlume".hashCode(), "Herr", UserRole.ADMIN));
    }

    public void generateTestProducts() {
        productList = new ArrayList<>();
        Product flieder = new Product("flieder", 0 ,0.5f);
        productList.add(flieder);

        Product rose = new Product("rose", 1, 1.15f);
        productList.add(rose);

        Product daisy = new Product("Gänseblümchen", 2, 0.33f);
        productList.add(daisy);
        
        Product mowLawn = new Product("Rasenmähen", 3, 15.99f);
        productList.add(mowLawn);
    }

    /**
     * Get the value of userList
     *
     * @return the value of userList
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * Set the value of userList
     *
     * @param userList new value of userList
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    /**
     * Get the value of productList
     *
     * @return the value of productList
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }

    /**
     * Set the value of productList
     *
     * @param productList new value of productList
     */
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

}
