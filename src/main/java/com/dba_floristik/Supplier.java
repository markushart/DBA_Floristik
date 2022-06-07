/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dba_floristik;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "supplier")
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findBySupid", query = "SELECT s FROM Supplier s WHERE s.supid = :supid"),
    @NamedQuery(name = "Supplier.findBySupname", query = "SELECT s FROM Supplier s WHERE s.supname = :supname"),
    @NamedQuery(name = "Supplier.findBySupcontactname", query = "SELECT s FROM Supplier s WHERE s.supcontactname = :supcontactname"),
    @NamedQuery(name = "Supplier.findBySupphone", query = "SELECT s FROM Supplier s WHERE s.supphone = :supphone"),
    @NamedQuery(name = "Supplier.findBySupemail", query = "SELECT s FROM Supplier s WHERE s.supemail = :supemail"),
    @NamedQuery(name = "Supplier.findBySupwww", query = "SELECT s FROM Supplier s WHERE s.supwww = :supwww")})
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SUPID")
    private Integer supid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUPNAME")
    private String supname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUPCONTACTNAME")
    private String supcontactname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUPPHONE")
    private String supphone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUPEMAIL")
    private String supemail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SUPWWW")
    private String supwww;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkSupid")
    private Collection<Product> productCollection;

    public Supplier() {
    }

    public Supplier(Integer supid) {
        this.supid = supid;
    }

    public Supplier(Integer supid, String supname, String supcontactname, String supphone, String supemail, String supwww) {
        this.supid = supid;
        this.supname = supname;
        this.supcontactname = supcontactname;
        this.supphone = supphone;
        this.supemail = supemail;
        this.supwww = supwww;
    }

    public Integer getSupid() {
        return supid;
    }

    public void setSupid(Integer supid) {
        this.supid = supid;
    }

    public String getSupname() {
        return supname;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public String getSupcontactname() {
        return supcontactname;
    }

    public void setSupcontactname(String supcontactname) {
        this.supcontactname = supcontactname;
    }

    public String getSupphone() {
        return supphone;
    }

    public void setSupphone(String supphone) {
        this.supphone = supphone;
    }

    public String getSupemail() {
        return supemail;
    }

    public void setSupemail(String supemail) {
        this.supemail = supemail;
    }

    public String getSupwww() {
        return supwww;
    }

    public void setSupwww(String supwww) {
        this.supwww = supwww;
    }

    public Collection<Product> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Product> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supid != null ? supid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supid == null && other.supid != null) || (this.supid != null && !this.supid.equals(other.supid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.resources.Supplier[ supid=" + supid + " ]";
    }
    
}
