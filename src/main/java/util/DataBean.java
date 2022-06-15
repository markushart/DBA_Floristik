/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import com.dba_floristik.Account;
import com.dba_floristik.Adress;
import com.dba_floristik.Customer;
import com.dba_floristik.Invoice;
import com.dba_floristik.Order1;
import com.dba_floristik.Orderdetailproduct;
import com.dba_floristik.Orderdetailservice;
import com.dba_floristik.Productcategory;
import com.dba_floristik.Product;
import com.dba_floristik.Service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
import javax.persistence.PersistenceUnit;
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
import model.ProductListItem;
import model.ServiceListItem;
import model.User;
import model.UserRole;

/**
 * Name: DataBean Aufgabe: Klasse für Interaktion mit Datenbank / dummy Daten
 * Version: 1.1 Letzte Änderung: 01.06.2022 Realisierung Markus Hartlage /
 * Sascha Nickel
 */
@Named(value = "dataBean")
@SessionScoped
@Transactional
public class DataBean implements Serializable {

    private ArrayList<User> userList;
    static final Logger LOGGER = Logger.getLogger(DataBean.class.getName());
    private static int id = 0;
    private List<Customer> customerObjectList;
    private List<Product> productObjectList;
    private List<Service> serviceObjectList;

    private ArrayList<Service> serviceList;

    private int size;
    private FacesContext context;
    private HttpSession session;
    @PersistenceUnit(unitName = "my_persistence_unit")
    private EntityManagerFactory emf;

    @Resource
    private UserTransaction ut;

    @Inject
    private Account actAccount;

    @PostConstruct
    public void init() {
        LOGGER.info("Konstruktor: DataBean");
        emf = Persistence.createEntityManagerFactory("my_persistence_unit", System.getProperties());
        if (emf.isOpen()) {
            LOGGER.info("DataBean: emf is open!");
            customerObjectList = new ArrayList<>();
            findAllCustomerObjects();
            serviceObjectList = new ArrayList<>();
            findAllServiceObjects();
            productObjectList = new ArrayList<>();
            findAllProductObjects();

        } else {
            LOGGER.info("DataBean: emf is not open!");
        }
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Databean: {0}", session.getId());
    }

    /**
     * Creates a new instance of DataBean
     */
    public DataBean() {

    }

    /**
     * Alle Kundenobjekte für Admin-Kundentabelle
     */
    private void findAllCustomerObjects() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
            this.customerObjectList = query.getResultList();
            this.size = this.getCustomerObjectList().size();
            LOGGER.log(Level.INFO, "Es wurden {0} Kunden in der DB gefunden.", size);
            userList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                userList.add(new User(customerObjectList.get(i).getCid(),
                        customerObjectList.get(i).getCfirstname(),
                        customerObjectList.get(i).getClastname(),
                        customerObjectList.get(i).getCsalutation(),
                        customerObjectList.get(i).getCemail(),
                        customerObjectList.get(i).getCphone()));
            }
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
            TypedQuery<Service> query = em.createNamedQuery("Service.findAll", Service.class);
            this.serviceObjectList = query.getResultList();
            this.size = this.getServiceObjectList().size();
            LOGGER.log(Level.INFO, "Es wurden {0} Service(s) in der DB gefunden.", size);
            serviceList = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                serviceList.add(new Service(
                        serviceObjectList.get(i).getServid(),
                        serviceObjectList.get(i).getServname(),
                        serviceObjectList.get(i).getServprice()));

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Alle Produktobjekte für Admin-Produkttabelle
     */
    private void findAllProductObjects() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
            this.productObjectList = query.getResultList();
            this.size = this.getServiceObjectList().size();
            LOGGER.log(Level.INFO, "Es wurden {0} Produkt(e) in der DB gefunden.", size);
            for (Product p : productObjectList) {
                LOGGER.log(Level.INFO, p.toString());
            }
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
            TypedQuery<Account> query = em.createNamedQuery("Account.findByAccname", Account.class);
            query.setParameter("accname", name);
            setActAccount(query.getSingleResult());
            LOGGER.log(Level.INFO, "Es wurde {0} als Account in der DB gefunden.", name);

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
            TypedQuery<Productcategory> query = em.createNamedQuery("Producttype.findByPtid", Productcategory.class);
            query.setParameter("ptid", pcatid);
            pcat = query.getSingleResult();
            LOGGER.log(Level.INFO, "Die ID wurde gefunden");
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return pcat;
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
        Product product = findProductByName(p.getPrname());
        p.setPrname(product.getPrname());
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
     * @param newAdressCollection
     * @return
     */
    public boolean persistCustomer(Customer newCustomer, Account newAccount, Collection<Adress> newAdressCollection) throws ConstraintViolationException {
        boolean ok;

        try {
            EntityManager em = emf.createEntityManager();//Payara-spezifisch
            em.joinTransaction();
            em.persist(newAccount); //zuerst Kunden-Account speichern
            newCustomer.setFkAccid(newAccount); //Account-Objekt im Kundenobjekt setzen
            em.persist(newCustomer); //Kunden speichern
            /* Adressen persistieren */
            for (Adress add : newAdressCollection) {
                add.setFkCid(newCustomer);
                em.persist(add);
            }
            
            // update collections
            /*
            ArrayList<Customer> cc = new ArrayList<>(1);
            cc.add(newCustomer);
            newAccount.setCustomerCollection(cc);
            newCustomer.setAdressCollection(newAdressCollection);
            em.persist(newAccount);
            em.persist(newCustomer);
            */

            ok = true;
            LOGGER.info("Registrieren ok (Customer mit Account)");
        } catch (IllegalStateException | SecurityException | ConstraintViolationException ex) {
            ok = false;
            throw ex;
        }
        return ok;
    }

    /**
     *
     * @param plist
     * @param slist
     * @param c
     * @return
     */
    public boolean persistShoppingCart(Customer c, ArrayList<ProductListItem> plist, ArrayList<ServiceListItem> slist) {

        /**
         * *****************************************************
         * achtung Sascha, ODPDATE könnte bei dir noch ODPATE heißen! ich habe
         * die Datenbank geändert! achtung Sascha, ODSDATE könnte bei dir noch
         * ODSATE heißen! ich habe die Datenbank geändert!
         *
         *
         *
         * *****************************************************
         */
        EntityManager em = emf.createEntityManager();
        em.joinTransaction();

        // new order for order1
        Order1 o = new Order1(0, new Date());
        o.setFkCid(c);
        em.persist(o);

        // new invoice
        Invoice i = new Invoice(0);
        i.setFkOid(o);
        em.persist(i);

        LOGGER.log(Level.INFO, "created new order {0}", o.getOid());

        for (ProductListItem pi : plist) {
            Product p = pi.getProduct();
            LOGGER.log(Level.INFO, "persisting product: {0}", p.toString());
            // new orderdetail for product, order date is now
            Orderdetailproduct odp = new Orderdetailproduct(0, (short) pi.getOrderAmount(), new Date());
            // set product and order id
            odp.setFkPrid(p);
            odp.setFkOid(o);
            try {
                em.persist(odp);
            } catch (ConstraintViolationException exp) {
                LOGGER.log(Level.SEVERE, "persisting did not succeed, orderdetailproduct was: {0}", odp.toString());
                // somehow rollback on error?
                return false;
            }

            Collection<Orderdetailproduct> opc = p.getOrderdetailproductCollection();
            // make sure collection is not null
            if (opc == null) {
                opc = new ArrayList<>();
            }
            opc.add(odp);
            em.persist(p);
        }

        for (ServiceListItem si : slist) {
            Service s = si.getService();
            // new orderdetail for service
            /* ODSAMOUNT is currently always set to 0 because website does not provide possibility
            *  to change it
             */
            Orderdetailservice ods = new Orderdetailservice(0, si.getServiceDate());
            // 
            ods.setFKServID(s);
            ods.setFkOid(o);
            try {
                em.persist(ods);
            } catch (ConstraintViolationException exs) {
                LOGGER.log(Level.SEVERE, "persisting did not succeed, orderdetailservice was: {0}", ods.toString());
                // somehow rollback on error?
                return false;
            }

            Collection<Orderdetailservice> osc = s.getOrderdetailserviceCollection();
            // make sure collection is not null
            if (osc == null) {
                osc = new ArrayList<>();
            }
            osc.add(ods);
            em.persist(s);
        }

        em.close();

        return true;
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
     * Get the value of serviceList
     *
     * @return the value of serviceList
     */
    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

}
