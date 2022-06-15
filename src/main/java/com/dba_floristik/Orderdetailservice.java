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
@Table(name = "orderdetailservice")
@NamedQueries({
    @NamedQuery(name = "Orderdetailservice.findAll", query = "SELECT o FROM Orderdetailservice o"),
    @NamedQuery(name = "Orderdetailservice.findByOdsid", query = "SELECT o FROM Orderdetailservice o WHERE o.odsid = :odsid"),
    @NamedQuery(name = "Orderdetailservice.findByOdsdate", query = "SELECT o FROM Orderdetailservice o WHERE o.odsdate = :odsdate")})
public class Orderdetailservice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODSID")
    private Integer odsid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODSDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date odsdate;
    @JoinColumn(name = "FK_OID", referencedColumnName = "OID")
    @ManyToOne(optional = false)
    private Order1 fkOid;
    @JoinColumn(name = "FK_ServID", referencedColumnName = "SERVID")
    @ManyToOne(optional = false)
    private Service fKServID;

    public Orderdetailservice() {
    }

    public Orderdetailservice(Integer odsid) {
        this.odsid = odsid;
    }

    public Orderdetailservice(Integer odsid, Date odsdate) {
        this.odsid = odsid;
        this.odsdate = odsdate;
    }

    public Integer getOdsid() {
        return odsid;
    }

    public void setOdsid(Integer odsid) {
        this.odsid = odsid;
    }

    public Date getOdsdate() {
        return odsdate;
    }

    public void setOdsdate(Date odsdate) {
        this.odsdate = odsdate;
    }

    public Order1 getFkOid() {
        return fkOid;
    }

    public void setFkOid(Order1 fkOid) {
        this.fkOid = fkOid;
    }

    public Service getFKServID() {
        return fKServID;
    }

    public void setFKServID(Service fKServID) {
        this.fKServID = fKServID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (odsid != null ? odsid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderdetailservice)) {
            return false;
        }
        Orderdetailservice other = (Orderdetailservice) object;
        if ((this.odsid == null && other.odsid != null) || (this.odsid != null && !this.odsid.equals(other.odsid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Orderdetailservice[ odsid=" + odsid + " ]";
    }
    
}
