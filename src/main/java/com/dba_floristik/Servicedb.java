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
@Table(name = "servicedb")
@NamedQueries({
    @NamedQuery(name = "Servicedb.findAll", query = "SELECT s FROM Servicedb s"),
    @NamedQuery(name = "Servicedb.findByServid", query = "SELECT s FROM Servicedb s WHERE s.servid = :servid"),
    @NamedQuery(name = "Servicedb.findByServname", query = "SELECT s FROM Servicedb s WHERE s.servname = :servname"),
    @NamedQuery(name = "Servicedb.findByServprice", query = "SELECT s FROM Servicedb s WHERE s.servprice = :servprice")})
public class Servicedb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SERVID")
    private Integer servid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SERVNAME")
    private String servname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVPRICE")
    private float servprice;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fKServID")
    private Collection<Orderdetailservice> orderdetailserviceCollection;
    @OneToMany(mappedBy = "fkServid")
    private Collection<Employeeservice> employeeserviceCollection;

    public Servicedb() {
    }

    public Servicedb(Integer servid) {
        this.servid = servid;
    }

    public Servicedb(Integer servid, String servname, float servprice) {
        this.servid = servid;
        this.servname = servname;
        this.servprice = servprice;
    }

    public Integer getServid() {
        return servid;
    }

    public void setServid(Integer servid) {
        this.servid = servid;
    }

    public String getServname() {
        return servname;
    }

    public void setServname(String servname) {
        this.servname = servname;
    }

    public float getServprice() {
        return servprice;
    }

    public void setServprice(float servprice) {
        this.servprice = servprice;
    }

    public Collection<Orderdetailservice> getOrderdetailserviceCollection() {
        return orderdetailserviceCollection;
    }

    public void setOrderdetailserviceCollection(Collection<Orderdetailservice> orderdetailserviceCollection) {
        this.orderdetailserviceCollection = orderdetailserviceCollection;
    }

    public Collection<Employeeservice> getEmployeeserviceCollection() {
        return employeeserviceCollection;
    }

    public void setEmployeeserviceCollection(Collection<Employeeservice> employeeserviceCollection) {
        this.employeeserviceCollection = employeeserviceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servid != null ? servid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicedb)) {
            return false;
        }
        Servicedb other = (Servicedb) object;
        if ((this.servid == null && other.servid != null) || (this.servid != null && !this.servid.equals(other.servid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Service[ servid=" + servid + " ]";
    }
    
}
