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
@Table(name = "adress")
@NamedQueries({
    @NamedQuery(name = "Adress.findAll", query = "SELECT a FROM Adress a"),
    @NamedQuery(name = "Adress.findByAdrid", query = "SELECT a FROM Adress a WHERE a.adrid = :adrid"),
    @NamedQuery(name = "Adress.findByAstreet", query = "SELECT a FROM Adress a WHERE a.astreet = :astreet"),
    @NamedQuery(name = "Adress.findByAcity", query = "SELECT a FROM Adress a WHERE a.acity = :acity"),
    @NamedQuery(name = "Adress.findByAfedstate", query = "SELECT a FROM Adress a WHERE a.afedstate = :afedstate"),
    @NamedQuery(name = "Adress.findByAcitycode", query = "SELECT a FROM Adress a WHERE a.acitycode = :acitycode"),
    @NamedQuery(name = "Adress.findByAcountry", query = "SELECT a FROM Adress a WHERE a.acountry = :acountry"),
    @NamedQuery(name = "Adress.findByAdate", query = "SELECT a FROM Adress a WHERE a.adate = :adate")})
public class Adress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ADRID")
    private Integer adrid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ASTREET")
    private String astreet;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ACITY")
    private String acity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "AFEDSTATE")
    private String afedstate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ACITYCODE")
    private String acitycode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ACOUNTRY")
    private String acountry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ADATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adate;
    @JoinColumn(name = "FK_CID", referencedColumnName = "CID")
    @ManyToOne(optional = false)
    private Customer fkCid;

    public Adress() {
    }

    public Adress(Integer adrid) {
        this.adrid = adrid;
    }

    public Adress(Integer adrid, String astreet, String acity, String afedstate, String acitycode, String acountry, Date adate) {
        this.adrid = adrid;
        this.astreet = astreet;
        this.acity = acity;
        this.afedstate = afedstate;
        this.acitycode = acitycode;
        this.acountry = acountry;
        this.adate = adate;
    }

    public Integer getAdrid() {
        return adrid;
    }

    public void setAdrid(Integer adrid) {
        this.adrid = adrid;
    }

    public String getAstreet() {
        return astreet;
    }

    public void setAstreet(String astreet) {
        this.astreet = astreet;
    }

    public String getAcity() {
        return acity;
    }

    public void setAcity(String acity) {
        this.acity = acity;
    }

    public String getAfedstate() {
        return afedstate;
    }

    public void setAfedstate(String afedstate) {
        this.afedstate = afedstate;
    }

    public String getAcitycode() {
        return acitycode;
    }

    public void setAcitycode(String acitycode) {
        this.acitycode = acitycode;
    }

    public String getAcountry() {
        return acountry;
    }

    public void setAcountry(String acountry) {
        this.acountry = acountry;
    }

    public Date getAdate() {
        return adate;
    }

    public void setAdate(Date adate) {
        this.adate = adate;
    }

    public Customer getFkCid() {
        return fkCid;
    }

    public void setFkCid(Customer fkCid) {
        this.fkCid = fkCid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adrid != null ? adrid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adress)) {
            return false;
        }
        Adress other = (Adress) object;
        if ((this.adrid == null && other.adrid != null) || (this.adrid != null && !this.adrid.equals(other.adrid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dba_floristik.Adress[ adrid=" + adrid + " ]";
    }
    
}
