/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import static javax.swing.UIManager.put;
import javax.transaction.UserTransaction;
import model.Product;
import model.Service;
import model.User;
import model.UserRole;
import com.dba_floristik.Costumer;

/**
 * Name:            DataBean    
 * Aufgabe:         Klasse für Interaktion mit Datenbank / dummy Daten
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Markus Hartlage
 */
@Named(value = "dataBean")
@SessionScoped
public class DataBean implements Serializable {

    static final Logger LOGGER = Logger.getLogger(DataBean.class.getName());
    private ArrayList<User> userList; //ohne DB
    
    private List<Costumer> costumerObjects;
    
    @PersistenceUnit(unitName="my_persistence_unit")//mit DB
    private EntityManagerFactory emf;//mit DB
    
    @Resource//mit DB
    private UserTransaction ut;//mit DB
    
    private ArrayList<Product> productList;

    private ArrayList<Service> serviceList;

    @PostConstruct
    public void init() {
        generateTestUsers();
        generateTestProducts();
    }

    /**
     * Creates a new instance of DataBean
     */
    public DataBean() {
        //Neu für Datenbankanbindung
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver","org.mariadb.jdbc.driver");
        emf = Persistence.createEntityManagerFactory("my_persistence_unit", properties);
        if(emf.isOpen()){
            findAllCostumerObjects();
        }
        else{
            //
        }
    //Neu für Datenbankanbindung
    }
    
    /**
     * @return the customerList
     */
    private void findAllCostumerObjects(){
        EntityManager em =emf.createEntityManager();
        TypedQuery<Costumer> query = em.createNamedQuery("Customer.findAll", Costumer.class);
        costumerObjects = query.getResultList();
        for(Costumer c:costumerObjects){
            System.out.println(c.getCfirstname());
        }
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
    

    /**
     *
     */
    public void generateTestProducts() {
        productList = new ArrayList<>();
        Product flieder = new Product("flieder", 0, 0.5f, 1);
        productList.add(flieder);

        Product rose = new Product("rose", 1, 1.15f, 1);
        productList.add(rose);

        Product daisy = new Product("Gänseblümchen", 2, 0.33f, 1);
        productList.add(daisy);
    }

    /**
     *
     */
    public void generateTestServices() {
        serviceList = new ArrayList<>();
        Service decoration = new Service("Dekoration", 3, 15.99f);
        serviceList.add(decoration);
        Service greening = new Service("Begrünung", 4, 7.85f);
        serviceList.add(greening);
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

    /**
     * Get the value of serviceList
     *
     * @return the value of serviceList
     */
    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    /**
     * Set the value of serviceList
     *
     * @param serviceList new value of serviceList
     */
    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
