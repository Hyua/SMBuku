/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cobabuku;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author asus
 */
@Entity
@Table(name = "DATABUKU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Databuku.findAll", query = "SELECT d FROM Databuku d")
    , @NamedQuery(name = "Databuku.findById", query = "SELECT d FROM Databuku d WHERE d.id = :id")
    , @NamedQuery(name = "Databuku.findByNamaBuku", query = "SELECT d FROM Databuku d WHERE d.namaBuku = :namaBuku")
    , @NamedQuery(name = "Databuku.findByPenerbitBuku", query = "SELECT d FROM Databuku d WHERE d.penerbitBuku = :penerbitBuku")
    , @NamedQuery(name = "Databuku.findByTanggalMasukBuku", query = "SELECT d FROM Databuku d WHERE d.tanggalMasukBuku = :tanggalMasukBuku")})
public class Databuku implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "NAMA_BUKU")
    private String namaBuku;
    @Column(name = "PENERBIT_BUKU")
    private String penerbitBuku;
    @Column(name = "TANGGAL_MASUK_BUKU")
    @Temporal(TemporalType.DATE)
    private Date tanggalMasukBuku;

    public Databuku() {
    }

    public Databuku(String id) {
        this.id = id;
    }

    public Databuku(String id, String namaBuku, String penerbitBuku, Date tanggalMasukBuku) {
        this.id = id;
        this.namaBuku = namaBuku;
        this.penerbitBuku = penerbitBuku;
        this.tanggalMasukBuku = tanggalMasukBuku;
    }

    Databuku(String text, String text0, String text1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getPenerbitBuku() {
        return penerbitBuku;
    }

    public void setPenerbitBuku(String penerbitBuku) {
        this.penerbitBuku = penerbitBuku;
    }

    public Date getTanggalMasukBuku() {
        return tanggalMasukBuku;
    }

    public void setTanggalMasukBuku(Date tanggalMasukBuku) {
        this.tanggalMasukBuku = tanggalMasukBuku;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Databuku)) {
            return false;
        }
        Databuku other = (Databuku) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cobabuku.Databuku[ id=" + id + " ]";
    }
    
    public String getSimpleTanggalMasukBuku() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(tanggalMasukBuku);
    }
    
}
