/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import com.dba_floristik.Account;
import com.dba_floristik.Customer;
import com.dba_floristik.Productcategory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import model.Product;
import model.Service;
import model.User;
import model.UserRole;

/**
 * Name:            DataBean    
 * Aufgabe:         Klasse für Interaktion mit Datenbank / dummy Daten
 * Version:         1.1
 * Letzte Änderung: 01.06.2022
 * Realisierung     Markus Hartlage / Sascha Nickel
 */
@Named(value = "dataBean")
@SessionScoped @Transactional
public class DataBean implements Serializable {
    private ArrayList<User> userList;
    static final Logger LOGGER = Logger.getLogger(DataBean.class.getName());
    private static int id = 0;
    private List<Customer> customerObjectList;
    private List<Product> productObjectList;
    private List<Service> serviceObjectList;

    
    private int size;
    private FacesContext context;
    private HttpSession session;
    private EntityManagerFactory emf;
    
    @Resource
    private UserTransaction ut;

    @Inject
    private Account actAccount;







    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Databean: {0}", session.getId());
    }

    /**
     * Creates a new instance of DataBean
     */
    public DataBean() {
        LOGGER.info("Konstruktor: DataBean");
        emf = Persistence.createEntityManagerFactory("my_persistence_unit", System.getProperties());
        if (emf.isOpen()) {
            customerObjectList = new ArrayList<>();
            findAllCustomerObjects();
            serviceObjectList = new ArrayList<>();
            findAllServiceObjects();
            findAccountForAccountName("kunde");
            findProductCategoryById(1);
        }
    }
    
    /**
     * Alle Kundenobjekte für Admin-Kundentabelle
     */
    private void findAllCustomerObjects() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query= em.createNamedQuery("Customer.findAll", Customer.class);
            this.customerObjectList = query.getResultList();
            this.size = this.getCustomerObjectList().size();
            LOGGER.log(Level.INFO,"Es wurden {0} Kunden in der DB gefunden.", size);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
     
    
     /**
     * Alle Produktobjekte für Admin-Servicetabelle
     */
    private void findAllServiceObjects() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Service> query= em.createNamedQuery("Service.findAll", Service.class);
            this.serviceObjectList = query.getResultList();
            this.size = this.getServiceObjectList().size();
            LOGGER.log(Level.INFO,"Es wurden {0} Service(s) in der DB gefunden.", size);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Klassenmethode --> Aufruf in RegisterBean
     *
     * @param name
     * @return
     */
    public Account findAccountForAccountName(String name) {
        LOGGER.log(Level.INFO,"findAccountForAccountName aufgerufen");
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Account> query= em.createNamedQuery("Account.findByAccname",Account.class);
            query.setParameter("accname", name);
            setActAccount(query.getSingleResult());
            //LOGGER.log(Level.INFO,"Es wurde {0} als Account in der DB gefunden.", name);
            LOGGER.log(Level.INFO,"Es wurde als Account in der DB gefunden.");
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return getActAccount();
    }
    
    /**
     * Verwendung in Converter: ProduktCategoryConverter der wiederum für die
     * entsprechende SelectOneMenu- Komponente zu Einsatz kommt
     *
     * @param pcatid 
     * @return
     */
    public Productcategory findProductCategoryById(int pcatid) {
        LOGGER.log(Level.INFO,"findProductCategoryById aufgerufen");
        Productcategory pcat = null;
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Productcategory> query = em.createNamedQuery("Producttype.findByPtid",Productcategory.class);
            query.setParameter("ptid", pcatid);
            pcat = query.getSingleResult();
            LOGGER.log(Level.INFO,"Die ID wurde gefunden");
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return pcat;
    }

    /**
     * Für Produktliste (DataTable und DataView)
     *
     * @return
     */
    public List<Product> findAllProductObjects() {
        List<Product> list = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query
                    = em.createNamedQuery("Product.findAll",
                            Product.class);
            list = query.getResultList();
            LOGGER.log(Level.INFO,
                    "Produktsuche: {0} gefunden!",
                    list.size());
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return list;
    }
    /**
     * Erzeugen einer gefilterten Produktliste
     *
     * @param word
     * @return
     */
    public List<Product> findProductForSearchWord(String word) {
        List<Product> list = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query
                    = em.createNamedQuery("Product.findByLikePrname",
                            Product.class);
            query.setParameter("prname", "%" + word + "%");
            list = query.getResultList();
            LOGGER.log(Level.INFO,
                    "Produktsuche: {0} gefunden!",
                    list.size());
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return list;
    }
    
    /**
     * Verwendung via Converter in SelectOneMenu-Komponente
     *
     * @return
     */
    public List<Productcategory> findAllProductCategories() {
        List<Productcategory> list = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Productcategory> query
                    = em.createNamedQuery("Producttype.findAll",
                            Productcategory.class);
            list = query.getResultList();
            LOGGER.log(Level.INFO,
                    "Produkt-Kategorien: {0} gefunden!",
                    list.size());
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return list;
    }
    
    /**
     * Suche eines Produkts für Merge-Operation (Update) ID-Schlüssel wird
     * benötigt!
     *
     * @param pName
     * @return
     */
    public Product findProductByName(String pName) {
        Product p = null;
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query
                    = em.createNamedQuery("Product.findByPrname",
                            Product.class);
            query.setParameter("prname", pName);
            p = query.getSingleResult();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return p;
    }
    


    
    
    public void setCustomerObjectList(List<Customer> customerObjectList) {
        this.customerObjectList = customerObjectList;
    }

    public List<Customer> getCustomerObjectList() {
        return customerObjectList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Account getActAccount() {
        return actAccount;
    }

    public void setActAccount(Account actAccount) {
        this.actAccount = actAccount;
    }
    
    public List<Product> getProductObjectList() {
        return productObjectList;
    }

    public void setProductObjectList(List<Product> productObjectList) {
        this.productObjectList = productObjectList;
    }

    public List<Service> getServiceObjectList() {
        return serviceObjectList;
    }

    public void setServiceObjectList(List<Service> serviceObjectList) {
        this.serviceObjectList = serviceObjectList;
    }
    
    
    /**
     * Old ohne datenbank
     *
     */
    
    private ArrayList<Product> productList;

    private ArrayList<Service> serviceList;

    //* ohne datenbank, löschen?
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



}
