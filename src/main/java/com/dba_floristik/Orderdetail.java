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
import javax.validation.constraints.Size;

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "orderdetail")
@NamedQueries({
    @NamedQuery(name = "Orderdetail.findAll", query = "SELECT o FROM Orderdetail o"),
    @NamedQuery(name = "Orderdetail.findByOdid", query = "SELECT o FROM Orderdetail o WHERE o.odid = :odid"),
    @NamedQuery(name = "Orderdetail.findByOdamount", query = "SELECT o FROM Orderdetail o WHERE o.odamount = :odamount"),
    @NamedQuery(name = "Orderdetail.findByOrdtype", query = "SELECT o FROM Orderdetail o WHERE o.ordtype = :ordtype"),
    @NamedQuery(name = "Orderdetail.findByOdate", query = "SELECT o FROM Orderdetail o WHERE o.odate = :odate")})
public class Orderdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODID")
    private Integer odid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODAMOUNT")
    private short odamount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "ORDTYPE")
    private String ordtype;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odate;
    @JoinColumn(name = "FK_OID", referencedColumnName = "OID")
    @ManyToOne(optional = false)
    private Order1 fkOid;
    @JoinColumn(name = "FK_PRID", referencedColumnName = "PRID")
    @ManyToOne
    private Product fkPrid;
    @JoinColumn(name = "FK_SERVID", referencedColumnName = "SERVID")
    @ManyToOne
    private Service fkServid;

    public Orderdetail() {
    }

    public Orderdetail(Integer odid) {
        this.odid = odid;
    }

    public Orderdetail(Integer odid, short odamount, String ordtype, Date odate) {
        this.odid = odid;
        this.odamount = odamount;
        this.ordtype = ordtype;
        this.odate = odate;
    }

    public Integer getOdid() {
        return odid;
    }

    public void setOdid(Integer odid) {
        this.odid = odid;
    }

    public short getOdamount() {
        return odamount;
    }

    public void setOdamount(short odamount) {
        this.odamount = odamount;
    }

    public String getOrdtype() {
        return ordtype;
    }

    public void setOrdtype(String ordtype) {
        this.ordtype = ordtype;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
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

    public Service getFkServid() {
        return fkServid;
    }

    public void setFkServid(Service fkServid) {
        this.fkServid = fkServid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (odid != null ? odid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderdetail)) {
            return false;
        }
        Orderdetail other = (Orderdetail) object;
        if ((this.odid == null && other.odid != null) || (this.odid != null && !this.odid.equals(other.odid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Orderdetail[ odid=" + odid + " ]";
    }
    
}
