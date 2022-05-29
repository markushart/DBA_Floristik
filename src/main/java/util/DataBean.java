/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import com.dba_floristik.Account;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.transaction.UserTransaction;
import model.Product;
import model.Service;
import model.User;
import model.UserRole;
import com.dba_floristik.Customer;
import com.dba_floristik.Productcategory;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

/**
 * Name:            DataBean    
 * Aufgabe:         Klasse für Interaktion mit Datenbank / dummy Daten
 * Version:         1.0
 * Letzte Änderung: 01.05.2022
 * Realisierung     Markus Hartlage
 */
@Named(value = "dataBean")
@SessionScoped @Transactional
public class DataBean implements Serializable {

    static final Logger LOGGER = Logger.getLogger(DataBean.class.getName());
    private ArrayList<User> userList; //ohne DB
    
    private List<Customer> customerObjectList; //mit DB



    private int size;
    private FacesContext context;

    
    private HttpSession session;
    @Inject
    private Account actAccount;//mit DB


    
    @PersistenceUnit(unitName="my_PU")//mit DB
    private EntityManagerFactory emf;//mit DB
    
    @Resource//mit DB
    private UserTransaction ut;//mit DB
    
    private ArrayList<Product> productList;
    private ArrayList<Service> serviceList;
   



    /**
     * Creates a new instance of DataBean
     */
    public DataBean() {
        LOGGER.info("Konstruktor: DataBean");
        emf = Persistence.createEntityManagerFactory("my_PU",System.getProperties());
        if (emf.isOpen()){
            customerObjectList = new ArrayList <>();
            findAllCustomerObjects();
        }    
    }
    
    @PostConstruct
    public void init() {
        //old
        generateTestUsers();
        generateTestProducts();
        //old
        context=FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Databean: {0}", session.getId());
    }
    
    /**
     * Alle Kundenobjekte für Admin-Kundentabelle
     * @return the customerList
     */
    private void findAllCustomerObjects(){
        
        
        
        EntityManager em =emf.createEntityManager();
        try{
        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
        this.customerObjectList=query.getResultList();
        this.size = this.getCustomerObjectList().size();
        LOGGER.log(Level.INFO, "Es wurden{0} Kunden in der DB gefunden.",size);
        }
        catch(Exception ex){
            LOGGER.log(Level.SEVERE,null,ex);
        }
        
    }
    
    /**
    * Klassenmethode --> Aufruf in RegisterBean
    *
    * @param name
    * @return
    */
    public Account findAccountForAccountName(String name) {
        try{
            EntityManager em = emf.createEntityManager();
            TypedQuery<Account> query = em.createNamedQuery("Account.findByAccname",Account.class);
            query.setParameter("Accname", name);
            setActAccount(query.getSingleResult());          
        } catch (Exception ex){
            LOGGER.info(ex.getMessage());
        }
        return getActAccount();
    }
    
    /**
    * Verwendung in Converter: ProduktTypeConverter
    * der wiederum für die entsprechende SelectOneMenu-
    * Komponente zu Einsatz kommt
    * @param pcatid
    * @return pcat
   
    */    
    
    public Productcategory findProductTypeById(int pcatid){
        Productcategory pcat = null;
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Productcategory> query = em.createNamedQuery("Productcategory.findByPcatid",Productcategory.class);
            query.setParameter("Pcatid", pcatid);
            pcat = query.getSingleResult();
        } catch (Exception ex) {
        LOGGER.info(ex.getMessage());
        }
        return pcat;
    }
    
   /**
    * Für Produktliste (DataTable und DataView)
    * @return
    */ 
    
    public List<Product> findAllProductObjects(){
        List<Product> list = new ArrayList<>();
        try{
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query = em.createNamedQuery("Product.findByAll",Product.class);
            list=query.getResultList();
            LOGGER.log(Level.INFO,"Produktsuche: {0} gefunden!", list.size());
        }
        catch (Exception ex) {
            LOGGER.info(ex.getMessage());   
        }
        return list;  
    }
    
    /**
     * * Erzeugen einer gefilterten Produktliste
    * @param word
    * @return
    */
    public List<Product> findProductForSearchWord(String word){
        List<Product> list = new ArrayList<>();
        try{
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query = em.createNamedQuery("Product.findByLikePRname",Product.class);
            query.setParameter("prname","%"+word+"%");
            list=query.getResultList();
            LOGGER.log(Level.INFO,"Produktsuche: {0} gefunden!", list.size());
        }
        catch (Exception ex) {
            LOGGER.info(ex.getMessage());   
        }
        return list;
    }
     
    /**
    * Verwendung via Converter in SelectOneMenu-Komponente
    * @return
    */
    public List<Productcategory> findAllProductTypes() {
        List<Productcategory> list = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Productcategory> query = em.createNamedQuery("Producttype.findAll",Productcategory.class);
            list = query.getResultList();
            LOGGER.log( Level.INFO,"Produkt-Typen: {0} gefunden!", list.size());
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return list;
    }
    
    /**
    * Suche eines Produkts für Merge-Operation (Update)
    * ID-Schlüssel wird benötigt!
    * @param pName
    * @return
    */
    public Product findProductByName(String pName){
        Product p = null;
        try {
            EntityManager em = emf.createEntityManager();
            TypedQuery<Product> query= em.createNamedQuery("Product.findByPrname",Product.class);
            query.setParameter("prname", pName);
            p = query.getSingleResult();
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return p;
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
    
    @SuppressWarnings("empty-statement")
    public boolean updateProduct(Product p) throws javax.transaction.RollbackException {
        boolean ok = true;
        //Erfragen des korrekten ID-Schlüssels für das zu ändernde Produkt
        Product product = findProductByName(p.getName());
        p.setId(product.getId());
        EntityManager em = emf.createEntityManager();
        try {
            //Payara-spezifisch
            final Context icontext = new InitialContext ();
            ut = (UserTransaction)(icontext).lookup("java:comp/UserTransaction");
            //(Normale) Transaktion: Erforderlich für INSERT,UPDATE,DELETE-Ops
            ut.begin();
            em.joinTransaction();
            em.merge(p);
            ut.commit();
            ok=true;
        } catch (IllegalStateException | SecurityException ex) {
            try {
                LOGGER.log(Level.SEVERE, null, ex);
                ut.rollback();
            } catch (IllegalStateException | SecurityException |
                SystemException ex1) {LOGGER.log(Level.SEVERE, null, ex1);
                }
            ok=false;
            } catch (NotSupportedException | SystemException |RollbackException | HeuristicMixedException |HeuristicRollbackException | NamingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            }
            return ok;
    }
    
    /**
    * Registrierung eines neuen Kunden mit zugehörigem
    * Account Verwendung in:
    * RegisterBean.java
    *
    * @param newCustomer
    * @param newAccount
    * @return
     * @throws javax.transaction.RollbackException
    */
    public boolean persistCustomer(Customer newCustomer, Account newAccount) throws javax.transaction.RollbackException {
        boolean ok = false;
        try {
            EntityManager em = emf.createEntityManager();
            //Payara-spezifisch
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
        } catch (IllegalStateException | SecurityException| HeuristicMixedException | HeuristicRollbackException| NotSupportedException | RollbackException| SystemException ex) {
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

    public List<Customer> getCustomerObjectList() {
        return customerObjectList;
    }

    public void setCustomerObjectList(List<Customer> customerObjectList) {
        this.customerObjectList = customerObjectList;
    }
    
        public Account getActAccount() {
        return actAccount;
    }

    public void setActAccount(Account actAccount) {
        this.actAccount = actAccount;
    }
    
    /**
     * Get the value of size
     *
     * @return the value of size
     */
    public int getSize() {
        return size;
    }

    /**
     * Set the value of size
     *
     * @param size new value of size
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * @return the session
     */
    public HttpSession getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }
}

