/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import model.Product;
import model.Service;
import model.User;
import model.UserRole;

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
    private ArrayList<User> userList;

    private ArrayList<Product> productList;
    
    private List<User> custommerobjectlist;

    private ArrayList<Service> serviceList;
    private Connection conn=null;//neu in seminar woche 7 inkl getter und setter

    @PostConstruct
    public void init() {
        generateTestUsers();
        generateTestProducts();
    }

    /**
     * Creates a new instance of DataBean
     */
    public DataBean() throws SQLException {
        try {
            //neu in seminar woche 7
            String driver = "org.mariadb.jdbc.Driver"; //Verbindung herstellen
            Class.forName(driver); //registriren des db-treibers
            String dbUrl ="jdbc:mariadb://localhost:3306/floristik";
            //verbindung zur datenbank herstellen
            setConn(DriverManager.getConnection(dbUrl, "dba", "dba"));//connection definieren 
            
            fillCostumerObjectListFromJDBC();
            fillProductObjectListFromJDBC();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //neu in seminar woche 7
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    private void fillCostumerObjectListFromJDBC() throws SQLException{
        try {
            String sql = "select * from costumer "
                    + "inner join account on accid=fk_accid "
                    + "order by clastname";
            Statement stmnt = conn.createStatement();
            ResultSet rs= stmnt.executeQuery(sql);
            
            while(rs.next()){
                custommerobjectlist.add(new User(
                //rs.getInt("CID"),// mit konstruktor in user anpassen
                rs.getString("Clastname"),
                rs.getString("cemail"),
                rs.getString("cphone"),
                rs.getString("accname"),
                rs.getString("accpwd"),
                rs.getDate("cbirthdate")   
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void fillProductObjectListFromJDBC(){
        try {
            String sql = "select * from product inner join productcategory on pcatid=fk_pcat order by prname ";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
            BigDecimal priceNetto = BigDecimal.valueOf(rs.getDouble("pricenetto").setscale(2,RoundingMode.Half_up));
                productObjectList.add(new Product (
                        rs.getString("prname"),
                        rs.getString("pcatname"),
                        rs.getString("prcomment"),
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBean.class.getName()).log(Level.SEVERE, null, ex);
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
