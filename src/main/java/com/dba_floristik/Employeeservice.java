/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dba_floristik;

import java.io.Serializable;
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

/**
 *
 * @author Sascha Nickel
 */
@Entity
@Table(name = "employeeservice")
@NamedQueries({
    @NamedQuery(name = "Employeeservice.findAll", query = "SELECT e FROM Employeeservice e"),
    @NamedQuery(name = "Employeeservice.findByEsid", query = "SELECT e FROM Employeeservice e WHERE e.esid = :esid")})
public class Employeeservice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ESID")
    private Integer esid;
    @JoinColumn(name = "FK_EID", referencedColumnName = "EID")
    @ManyToOne
    private Employee fkEid;
    @JoinColumn(name = "FK_SERVID", referencedColumnName = "SERVID")
    @ManyToOne
    private Servicedb fkServid;

    public Employeeservice() {
    }

    public Employeeservice(Integer esid) {
        this.esid = esid;
    }

    public Integer getEsid() {
        return esid;
    }

    public void setEsid(Integer esid) {
        this.esid = esid;
    }

    public Employee getFkEid() {
        return fkEid;
    }

    public void setFkEid(Employee fkEid) {
        this.fkEid = fkEid;
    }

    public Servicedb getFkServid() {
        return fkServid;
    }

    public void setFkServid(Servicedb fkServid) {
        this.fkServid = fkServid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (esid != null ? esid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employeeservice)) {
            return false;
        }
        Employeeservice other = (Employeeservice) object;
        if ((this.esid == null && other.esid != null) || (this.esid != null && !this.esid.equals(other.esid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Employeeservice[ esid=" + esid + " ]";
    }
    
}
