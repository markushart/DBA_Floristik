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
@Table(name = "order1")
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findByOid", query = "SELECT o FROM Order1 o WHERE o.oid = :oid"),
    @NamedQuery(name = "Order1.findByOdelivdate", query = "SELECT o FROM Order1 o WHERE o.odelivdate = :odelivdate"),
    @NamedQuery(name = "Order1.findByOstatus", query = "SELECT o FROM Order1 o WHERE o.ostatus = :ostatus"),
    @NamedQuery(name = "Order1.findByBcomment", query = "SELECT o FROM Order1 o WHERE o.bcomment = :bcomment"),
    @NamedQuery(name = "Order1.findByOchangedate", query = "SELECT o FROM Order1 o WHERE o.ochangedate = :ochangedate")})
public class Order1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OID")
    private Integer oid;
    @Column(name = "ODELIVDATE")
    @Temporal(TemporalType.DATE)
    private Date odelivdate;
    @Size(max = 14)
    @Column(name = "OSTATUS")
    private String ostatus;
    @Size(max = 256)
    @Column(name = "BCOMMENT")
    private String bcomment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OCHANGEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ochangedate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkOid")
    private Collection<Invoice> invoiceCollection;
    @JoinColumn(name = "FK_CID", referencedColumnName = "CID")
    @ManyToOne
    private Customer fkCid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkOid")
    private Collection<Orderdetail> orderdetailCollection;

    public Order1() {
    }

    public Order1(Integer oid) {
        this.oid = oid;
    }

    public Order1(Integer oid, Date ochangedate) {
        this.oid = oid;
        this.ochangedate = ochangedate;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Date getOdelivdate() {
        return odelivdate;
    }

    public void setOdelivdate(Date odelivdate) {
        this.odelivdate = odelivdate;
    }

    public String getOstatus() {
        return ostatus;
    }

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    public String getBcomment() {
        return bcomment;
    }

    public void setBcomment(String bcomment) {
        this.bcomment = bcomment;
    }

    public Date getOchangedate() {
        return ochangedate;
    }

    public void setOchangedate(Date ochangedate) {
        this.ochangedate = ochangedate;
    }

    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    public Customer getFkCid() {
        return fkCid;
    }

    public void setFkCid(Customer fkCid) {
        this.fkCid = fkCid;
    }

    public Collection<Orderdetail> getOrderdetailCollection() {
        return orderdetailCollection;
    }

    public void setOrderdetailCollection(Collection<Orderdetail> orderdetailCollection) {
        this.orderdetailCollection = orderdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (oid != null ? oid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.oid == null && other.oid != null) || (this.oid != null && !this.oid.equals(other.oid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Order1[ oid=" + oid + " ]";
    }
    
}
