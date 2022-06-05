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
@Table(name = "productdb")
@NamedQueries({
    @NamedQuery(name = "Productdb.findAll", query = "SELECT p FROM Productdb p"),
    @NamedQuery(name = "Productdb.findByPrid", query = "SELECT p FROM Productdb p WHERE p.prid = :prid"),
    @NamedQuery(name = "Productdb.findByPrname", query = "SELECT p FROM Productdb p WHERE p.prname = :prname"),
    @NamedQuery(name = "Productdb.findByPramount", query = "SELECT p FROM Productdb p WHERE p.pramount = :pramount"),
    @NamedQuery(name = "Productdb.findByPpricenetto", query = "SELECT p FROM Productdb p WHERE p.ppricenetto = :ppricenetto"),
    @NamedQuery(name = "Productdb.findByPrimodifdate", query = "SELECT p FROM Productdb p WHERE p.primodifdate = :primodifdate")})
public class Productdb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRID")
    private Integer prid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PRNAME")
    private String prname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRAMOUNT")
    private int pramount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PPRICENETTO")
    private float ppricenetto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIMODIFDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date primodifdate;
    @JoinColumn(name = "FK_PCATID", referencedColumnName = "PCATID")
    @ManyToOne(optional = false)
    private Productcategory fkPcatid;
    @JoinColumn(name = "FK_SUPID", referencedColumnName = "SUPID")
    @ManyToOne(optional = false)
    private Supplier fkSupid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkPrid")
    private Collection<Orderdetailproduct> orderdetailproductCollection;

    public Productdb() {
    }

    public Productdb(Integer prid) {
        this.prid = prid;
    }

    public Productdb(Integer prid, String prname, int pramount, float ppricenetto, Date primodifdate) {
        this.prid = prid;
        this.prname = prname;
        this.pramount = pramount;
        this.ppricenetto = ppricenetto;
        this.primodifdate = primodifdate;
    }

    public Integer getPrid() {
        return prid;
    }

    public void setPrid(Integer prid) {
        this.prid = prid;
    }

    public String getPrname() {
        return prname;
    }

    public void setPrname(String prname) {
        this.prname = prname;
    }

    public int getPramount() {
        return pramount;
    }

    public void setPramount(int pramount) {
        this.pramount = pramount;
    }

    public float getPpricenetto() {
        return ppricenetto;
    }

    public void setPpricenetto(float ppricenetto) {
        this.ppricenetto = ppricenetto;
    }

    public Date getPrimodifdate() {
        return primodifdate;
    }

    public void setPrimodifdate(Date primodifdate) {
        this.primodifdate = primodifdate;
    }

    public Productcategory getFkPcatid() {
        return fkPcatid;
    }

    public void setFkPcatid(Productcategory fkPcatid) {
        this.fkPcatid = fkPcatid;
    }

    public Supplier getFkSupid() {
        return fkSupid;
    }

    public void setFkSupid(Supplier fkSupid) {
        this.fkSupid = fkSupid;
    }

    public Collection<Orderdetailproduct> getOrderdetailproductCollection() {
        return orderdetailproductCollection;
    }

    public void setOrderdetailproductCollection(Collection<Orderdetailproduct> orderdetailproductCollection) {
        this.orderdetailproductCollection = orderdetailproductCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prid != null ? prid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productdb)) {
            return false;
        }
        Productdb other = (Productdb) object;
        if ((this.prid == null && other.prid != null) || (this.prid != null && !this.prid.equals(other.prid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Product[ prid=" + prid + " ]";
    }
    
}
