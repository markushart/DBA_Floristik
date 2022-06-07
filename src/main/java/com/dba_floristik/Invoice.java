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

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "invoice")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvid", query = "SELECT i FROM Invoice i WHERE i.invid = :invid"),
    @NamedQuery(name = "Invoice.findByInvdate", query = "SELECT i FROM Invoice i WHERE i.invdate = :invdate")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "INVID")
    private Integer invid;
    @Column(name = "INVDATE")
    @Temporal(TemporalType.DATE)
    private Date invdate;
    @JoinColumn(name = "FK_OID", referencedColumnName = "OID")
    @ManyToOne(optional = false)
    private Order1 fkOid;

    public Invoice() {
    }

    public Invoice(Integer invid) {
        this.invid = invid;
    }

    public Integer getInvid() {
        return invid;
    }

    public void setInvid(Integer invid) {
        this.invid = invid;
    }

    public Date getInvdate() {
        return invdate;
    }

    public void setInvdate(Date invdate) {
        this.invdate = invdate;
    }

    public Order1 getFkOid() {
        return fkOid;
    }

    public void setFkOid(Order1 fkOid) {
        this.fkOid = fkOid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invid != null ? invid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invid == null && other.invid != null) || (this.invid != null && !this.invid.equals(other.invid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.resources.Invoice[ invid=" + invid + " ]";
    }
    
}
