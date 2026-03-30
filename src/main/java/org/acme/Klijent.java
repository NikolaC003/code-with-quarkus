/*package org.acme;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@NamedQuery(name = Klijent.GET_ALL_KLIJENTI, query = "Select k from Klijent k")public class Klijent {

    public static final String GET_ALL_KLIJENTI = "GetAllKlijenti";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "klijent_seq")
    @SequenceGenerator(name = "klijent_seq", sequenceName = "klijent_seq", allocationSize = 1)
    public Long id;

    public String ime;
    public String prezime;
    public String brTelefona;


    public Klijent() {
    }

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getIme() { 
        return ime; 
    }
    public void setIme(String ime) { 
        this.ime = ime; 
    }

    public String getPrezime() { 
        return prezime; 
    }
    public void setPrezime(String prezime) { 
        this.prezime = prezime; 
    }

    public String getBrTelefona() { 
        return brTelefona; 
    }
    public void setBrTelefona(String brTelefona) { 
        this.brTelefona = brTelefona; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Klijent klijent = (Klijent) o;
        return Objects.equals(id, klijent.id) && 
               Objects.equals(ime, klijent.ime) && 
               Objects.equals(prezime, klijent.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime);
    }
}*/
package org.acme;
package me.fit.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Klijent.GET_ALL_KLIJENTI, query = "Select k.id, k.ime, k.prezime from Klijent k")
@NamedQuery(name = Klijent.GET_KLIJENT_BY_NAME, query = "Select k from Klijent k where k.ime = :imeK")
public class Klijent {

    public static final String GET_ALL_KLIJENTI = "GetAllKlijenti";
    public static final String GET_KLIJENT_BY_NAME = "GetKlijentByName";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "klijent_seq")
    @SequenceGenerator(name = "klijent_seq", sequenceName = "klijent_seq", allocationSize = 1)
    private Long id;

    private String ime;

    private String prezime;

    public Klijent() {
    }

    public Klijent(Long id, String ime, String prezime) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "klijent_id")
    private List<Automobil> automobili = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public List<Automobil> getAutomobili() {
        return automobili;
    }

    public void setAutomobili(List<Automobil> automobili) {
        this.automobili = automobili;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Klijent klijent)) return false;
        return Objects.equals(id, klijent.id) && 
               Objects.equals(ime, klijent.ime) && 
               Objects.equals(prezime, klijent.prezime) && 
               Objects.equals(automobili, klijent.automobili);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, automobili);
    }

    @Override
    public String toString() {
        return "Klijent{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", automobili=" + automobili +
                '}';
    }
}
/*
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "klijent")
public class Klijent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "klijent_seq")
    @SequenceGenerator(name = "klijent_seq", sequenceName = "klijent_seq", allocationSize = 1)
    private Long id;

    private String ime;
    private String prezime;

    // Tačka 2: OneToMany sa FetchType.LAZY
    // Tačka 4: CascadeType.ALL omogućava dodavanje klijenta zajedno sa automobilima
    @OneToMany(mappedBy = "klijent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference // Sprječava beskonačnu rekurziju u JSON-u
    private List<Automobil> automobili = new ArrayList<>();

    public Klijent() {}

    public Long getId() {
    return id; 
    }
    public String getIme() {
         return ime; 
         }
    public void setIme(String ime) { 
        this.ime = ime; 
        }
    public String getPrezime() {
         return prezime; 
         }
    public void setPrezime(String prezime) {
        this.prezime = prezime; 
        }
    public List<Automobil> getAutomobili() {
         return automobili; 
         }
    public void setAutomobili(List<Automobil> automobili) {
         this.automobili = automobili; }
}*/