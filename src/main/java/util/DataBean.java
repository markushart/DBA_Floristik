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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
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
            System.out.println(customerObjectList.get(0));
            customerObjectList.get(0).getCid();
            userList = new ArrayList<>();
            
            
            userList.add(new User(customerObjectList.get(0).getCid(),
                    customerObjectList.get(0).getCfirstname(),
                    customerObjectList.get(0).getClastname(),
                    customerObjectList.get(0).getCsalutation(),
                    customerObjectList.get(0).getCemail(),
                    customerObjectList.get(0).getCphone()));
                    
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
     
    
     /**
     * Alle Serviceobjekte für Admin-Servicetabelle
     */
    private void findAllServiceObjects() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Service> query= em.createNamedQuery("Service.findAll", Service.class);
            this.serviceObjectList = query.getResultList();
            this.size = this.getServiceObjectList().size();
            LOGGER.log(Level.INFO,"Es wurden {0} Service(s) in der DB gefunden.", size);
            //serviceList = new ArrayList<>();
            //System.out.println(serviceObjectList.get(0).getName());
            //<serviceList.add(new Service(serviceList.get(0).getName(),
             //       serviceList.get(0).getId(),
              //      serviceList.get(0).getPrice()));
              System.out.println(serviceObjectList.get(0).getId());
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
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Account> query= em.createNamedQuery("Account.findByAccname",Account.class);
            query.setParameter("accname", name);
            setActAccount(query.getSingleResult());
            LOGGER.log(Level.INFO,"Es wurde {0} als Account in der DB gefunden.", name);
            
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
    @SuppressWarnings("empty-statement")
    public boolean updateProduct(Product p) throws javax.transaction.RollbackException {
        boolean ok = true;
//Erfragen des korrekten ID-Schlüssels für das zu ändernde Produkt
        Product product = findProductByName(p.getName());
        p.setName(product.getName());
        EntityManager em = emf.createEntityManager();
        try {
//Payara-spezifisch
            final Context icontext = new InitialContext();
            ut = (UserTransaction) (icontext).lookup("java:comp/UserTransaction");
//(Normale) Transaktion: Erforderlich für INSERT,UPDATE,DELETE-Ops
            ut.begin();
            em.joinTransaction();
            em.merge(p);
            ut.commit();
            ok = true;
        } catch (IllegalStateException | SecurityException ex) {
            try {
                LOGGER.log(Level.SEVERE, null, ex);
                ut.rollback();
            } catch (IllegalStateException | SecurityException
                    | SystemException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
            ok = false;
        } catch (NotSupportedException | SystemException
                | RollbackException | HeuristicMixedException
                | HeuristicRollbackException | NamingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return ok;
    }
    /**
     * Registrierung eines neuen Kunden mit zugehörigem Account Verwendung in:
     * RegisterBean.java
     *
     * @param newCustomer
     * @param newAccount
     * @return
     */
    public boolean persistCustomer(Customer newCustomer,
            Account newAccount) throws NamingException, NotSupportedException, javax.transaction.RollbackException {
        boolean ok = false;
        try {
            EntityManager em = emf.createEntityManager();//Payara-spezifisch
            final Context icontext = new InitialContext();
            ut = (UserTransaction) (icontext).lookup("java:comp/UserTransaction");
            ut.begin();
            em.joinTransaction();
            em.persist(newAccount); //zuerst Kunden-Account speichern
            newCustomer.setFkAccid(newAccount); //Account-Objekt im Kundenobjekt setzen
            em.persist(newCustomer); //Kunden speichern
            ut.commit();
            ok = true;
            LOGGER.info("Registrieren ok (Customer mit Account)");
        } catch (IllegalStateException | SecurityException
                | HeuristicMixedException | HeuristicRollbackException
                | NotSupportedException | RollbackException
                | SystemException ex) {
            try {
                int status = Status.STATUS_NO_TRANSACTION;
                if (status != ut.getStatus()) {
                    ut.rollback();
                }
            } catch (IllegalStateException
                    | SecurityException
                    | SystemException ex1) {
                LOGGER.log(Level.SEVERE, null, ex1);
            }
        } catch (ConstraintViolationException e) {
            LOGGER.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations()
                    .forEach(err -> LOGGER.log(Level.SEVERE, err.toString()));
        } catch (NamingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return ok;
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
/*
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
*/
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
        /*
        serviceList = new ArrayList<>();
        Service decoration = new Service("Dekoration", 3, 15.99f);
        serviceList.add(decoration);
        Service greening = new Service("Begrünung", 4, 7.85f);
        serviceList.add(greening);
        */
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
