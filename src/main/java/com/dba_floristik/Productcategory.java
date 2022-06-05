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
@Table(name = "productcategory")
@NamedQueries({
    @NamedQuery(name = "Productcategory.findAll", query = "SELECT p FROM Productcategory p"),
    @NamedQuery(name = "Productcategory.findByPcatid", query = "SELECT p FROM Productcategory p WHERE p.pcatid = :pcatid"),
    @NamedQuery(name = "Productcategory.findByPcatname", query = "SELECT p FROM Productcategory p WHERE p.pcatname = :pcatname"),
    @NamedQuery(name = "Productcategory.findByPcatorigin", query = "SELECT p FROM Productcategory p WHERE p.pcatorigin = :pcatorigin")})
public class Productcategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PCATID")
    private Integer pcatid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PCATNAME")
    private String pcatname;
    @Size(max = 16)
    @Column(name = "PCATORIGIN")
    private String pcatorigin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkPcatid")
    private Collection<Productdb> productCollection;

    public Productcategory() {
    }

    public Productcategory(Integer pcatid) {
        this.pcatid = pcatid;
    }

    public Productcategory(Integer pcatid, String pcatname) {
        this.pcatid = pcatid;
        this.pcatname = pcatname;
    }

    public Integer getPcatid() {
        return pcatid;
    }

    public void setPcatid(Integer pcatid) {
        this.pcatid = pcatid;
    }

    public String getPcatname() {
        return pcatname;
    }

    public void setPcatname(String pcatname) {
        this.pcatname = pcatname;
    }

    public String getPcatorigin() {
        return pcatorigin;
    }

    public void setPcatorigin(String pcatorigin) {
        this.pcatorigin = pcatorigin;
    }

    public Collection<Productdb> getProductCollection() {
        return productCollection;
    }

    public void setProductCollection(Collection<Productdb> productCollection) {
        this.productCollection = productCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pcatid != null ? pcatid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productcategory)) {
            return false;
        }
        Productcategory other = (Productcategory) object;
        if ((this.pcatid == null && other.pcatid != null) || (this.pcatid != null && !this.pcatid.equals(other.pcatid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Productcategory[ pcatid=" + pcatid + " ]";
    }
    
}
