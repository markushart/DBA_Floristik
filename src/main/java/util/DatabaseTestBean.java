/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import model.Product;

/**
 *
 * @author Sascha Nickel
 */
@Named(value = "databaseTestBean")
@SessionScoped
public class DatabaseTestBean implements Serializable {

    static final Logger LOGGER= Logger.getLogger(DatabaseTestBean.class.getName());


    private FacesContext context;
    private HttpSession session;
    
    @PersistenceUnit (unitName = "my_persistence_unit")
    private EntityManagerFactory emf;

    /**
     * Creates a new instance of DatabaseTestBean
     */
    public DatabaseTestBean() {
    }
    
    @PostConstruct
    public void init() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(false);
        LOGGER.log(Level.INFO, "Databean: {0}", session.getId());
    }
    
    /**
     * Abfrage der Produkte einer bestimmten Kategorie Alternative: in Java aus
     * der Liste aller Produkte per foreach die mit einem betimmten
     * Kategorienamen "herausfischen".
     *
     * @param catName
     * @return
     */
    public List<Product> findProductsByCategoryName(String catName) {
        List<Product> list = null;
        EntityManager em = emf.createEntityManager();
        final String SQL
                = "SELECT p FROM Product p "
                + "INNER JOIN p.fkPcatid cid "
                + "WHERE cid.pcatname = :pcatname";
        try {
            TypedQuery<Product> query = em.createQuery(SQL, Product.class);
            query.setParameter("pcatname", catName);
            list = query.getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return list;
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
}
