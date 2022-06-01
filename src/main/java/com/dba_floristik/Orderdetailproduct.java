/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dba_floristik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "orderdetailproduct")
@NamedQueries({
    @NamedQuery(name = "Orderdetailproduct.findAll", query = "SELECT o FROM Orderdetailproduct o"),
    @NamedQuery(name = "Orderdetailproduct.findByOdpid", query = "SELECT o FROM Orderdetailproduct o WHERE o.odpid = :odpid"),
    @NamedQuery(name = "Orderdetailproduct.findByOdpamount", query = "SELECT o FROM Orderdetailproduct o WHERE o.odpamount = :odpamount"),
    @NamedQuery(name = "Orderdetailproduct.findByOdpate", query = "SELECT o FROM Orderdetailproduct o WHERE o.odpate = :odpate")})
public class Orderdetailproduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODPID")
    private Integer odpid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODPAMOUNT")
    private short odpamount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODPATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odpate;
    @JoinColumn(name = "FK_OID", referencedColumnName = "OID")
    @ManyToOne(optional = false)
    private Order1 fkOid;
    @JoinColumn(name = "FK_PRID", referencedColumnName = "PRID")
    @ManyToOne(optional = false)
    private Product fkPrid;

    public Orderdetailproduct() {
    }

    public Orderdetailproduct(Integer odpid) {
        this.odpid = odpid;
    }

    public Orderdetailproduct(Integer odpid, short odpamount, Date odpate) {
        this.odpid = odpid;
        this.odpamount = odpamount;
        this.odpate = odpate;
    }

    public Integer getOdpid() {
        return odpid;
    }

    public void setOdpid(Integer odpid) {
        this.odpid = odpid;
    }

    public short getOdpamount() {
        return odpamount;
    }

    public void setOdpamount(short odpamount) {
        this.odpamount = odpamount;
    }

    public Date getOdpate() {
        return odpate;
    }

    public void setOdpate(Date odpate) {
        this.odpate = odpate;
    }

    public Order1 getFkOid() {
        return fkOid;
    }

    public void setFkOid(Order1 fkOid) {
        this.fkOid = fkOid;
    }

    public Product getFkPrid() {
        return fkPrid;
    }

    public void setFkPrid(Product fkPrid) {
        this.fkPrid = fkPrid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (odpid != null ? odpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderdetailproduct)) {
            return false;
        }
        Orderdetailproduct other = (Orderdetailproduct) object;
        if ((this.odpid == null && other.odpid != null) || (this.odpid != null && !this.odpid.equals(other.odpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Orderdetailproduct[ odpid=" + odpid + " ]";
    }
    
}
