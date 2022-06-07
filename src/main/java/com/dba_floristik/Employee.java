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
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByEid", query = "SELECT e FROM Employee e WHERE e.eid = :eid"),
    @NamedQuery(name = "Employee.findByEfirstname", query = "SELECT e FROM Employee e WHERE e.efirstname = :efirstname"),
    @NamedQuery(name = "Employee.findByElastname", query = "SELECT e FROM Employee e WHERE e.elastname = :elastname"),
    @NamedQuery(name = "Employee.findByEsalutation", query = "SELECT e FROM Employee e WHERE e.esalutation = :esalutation"),
    @NamedQuery(name = "Employee.findByEsalary", query = "SELECT e FROM Employee e WHERE e.esalary = :esalary")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EID")
    private Integer eid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "EFIRSTNAME")
    private String efirstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ELASTNAME")
    private String elastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "ESALUTATION")
    private String esalutation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESALARY")
    private float esalary;
    @OneToMany(mappedBy = "fkEid")
    private Collection<Employeeservice> employeeserviceCollection;

    public Employee() {
    }

    public Employee(Integer eid) {
        this.eid = eid;
    }

    public Employee(Integer eid, String efirstname, String elastname, String esalutation, float esalary) {
        this.eid = eid;
        this.efirstname = efirstname;
        this.elastname = elastname;
        this.esalutation = esalutation;
        this.esalary = esalary;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEfirstname() {
        return efirstname;
    }

    public void setEfirstname(String efirstname) {
        this.efirstname = efirstname;
    }

    public String getElastname() {
        return elastname;
    }

    public void setElastname(String elastname) {
        this.elastname = elastname;
    }

    public String getEsalutation() {
        return esalutation;
    }

    public void setEsalutation(String esalutation) {
        this.esalutation = esalutation;
    }

    public float getEsalary() {
        return esalary;
    }

    public void setEsalary(float esalary) {
        this.esalary = esalary;
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
        hash += (eid != null ? eid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.eid == null && other.eid != null) || (this.eid != null && !this.eid.equals(other.eid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.resources.Employee[ eid=" + eid + " ]";
    }
    
}
