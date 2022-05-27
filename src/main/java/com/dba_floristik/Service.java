/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dba_floristik;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "service")
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
    @NamedQuery(name = "Service.findByServid", query = "SELECT s FROM Service s WHERE s.servid = :servid"),
    @NamedQuery(name = "Service.findByServname", query = "SELECT s FROM Service s WHERE s.servname = :servname"),
    @NamedQuery(name = "Service.findByServprice", query = "SELECT s FROM Service s WHERE s.servprice = :servprice")})
public class Service implements Serializable {

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
    @OneToMany(mappedBy = "fkServid")
    private Collection<Employeeservice> employeeserviceCollection;
    @OneToMany(mappedBy = "fkServid")
    private Collection<Orderdetail> orderdetailCollection;

    public Service() {
    }

    public Service(Integer servid) {
        this.servid = servid;
    }

    public Service(Integer servid, String servname, float servprice) {
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

    public Collection<Employeeservice> getEmployeeserviceCollection() {
        return employeeserviceCollection;
    }

    public void setEmployeeserviceCollection(Collection<Employeeservice> employeeserviceCollection) {
        this.employeeserviceCollection = employeeserviceCollection;
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
        hash += (servid != null ? servid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
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
