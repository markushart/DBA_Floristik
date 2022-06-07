/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dba_floristik;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCid", query = "SELECT c FROM Customer c WHERE c.cid = :cid"),
    @NamedQuery(name = "Customer.findByCfirstname", query = "SELECT c FROM Customer c WHERE c.cfirstname = :cfirstname"),
    @NamedQuery(name = "Customer.findByClastname", query = "SELECT c FROM Customer c WHERE c.clastname = :clastname"),
    @NamedQuery(name = "Customer.findByCsalutation", query = "SELECT c FROM Customer c WHERE c.csalutation = :csalutation"),
    @NamedQuery(name = "Customer.findByCemail", query = "SELECT c FROM Customer c WHERE c.cemail = :cemail"),
    @NamedQuery(name = "Customer.findByCphone", query = "SELECT c FROM Customer c WHERE c.cphone = :cphone"),
    @NamedQuery(name = "Customer.findByCbirthdate", query = "SELECT c FROM Customer c WHERE c.cbirthdate = :cbirthdate")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CID")
    private Integer cid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CFIRSTNAME")
    private String cfirstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CLASTNAME")
    private String clastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "CSALUTATION")
    private String csalutation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CEMAIL")
    private String cemail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CPHONE")
    private String cphone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CBIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date cbirthdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkCid")
    private Collection<Adress> adressCollection;
    @OneToMany(mappedBy = "fkCid")
    private Collection<Order1> order1Collection;
    @JoinColumn(name = "FK_ACCID", referencedColumnName = "ACCID")
    @ManyToOne(optional = false)
    private Account fkAccid;

    public Customer() {
    }

    public Customer(Integer cid) {
        this.cid = cid;
    }

    public Customer(Integer cid, String cfirstname, String clastname, String csalutation, String cemail, String cphone, Date cbirthdate) {
        this.cid = cid;
        this.cfirstname = cfirstname;
        this.clastname = clastname;
        this.csalutation = csalutation;
        this.cemail = cemail;
        this.cphone = cphone;
        this.cbirthdate = cbirthdate;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCfirstname() {
        return cfirstname;
    }

    public void setCfirstname(String cfirstname) {
        this.cfirstname = cfirstname;
    }

    public String getClastname() {
        return clastname;
    }

    public void setClastname(String clastname) {
        this.clastname = clastname;
    }

    public String getCsalutation() {
        return csalutation;
    }

    public void setCsalutation(String csalutation) {
        this.csalutation = csalutation;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public Date getCbirthdate() {
        return cbirthdate;
    }

    public void setCbirthdate(Date cbirthdate) {
        this.cbirthdate = cbirthdate;
    }

    public Collection<Adress> getAdressCollection() {
        return adressCollection;
    }

    public void setAdressCollection(Collection<Adress> adressCollection) {
        this.adressCollection = adressCollection;
    }

    public Collection<Order1> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.order1Collection = order1Collection;
    }

    public Account getFkAccid() {
        return fkAccid;
    }

    public void setFkAccid(Account fkAccid) {
        this.fkAccid = fkAccid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Customer[ cid=" + cid + " ]";
    }
    
}
