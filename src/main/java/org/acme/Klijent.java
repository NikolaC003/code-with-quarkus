package org.acme;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Klijent.GET_ALL_KLIJENTI, query = "Select k from Klijent k")
@NamedQuery(name = Klijent.GET_KLIJENT_BY_NAME, query = "Select k from Klijent k where k.ime = :imeK")
public class Klijent {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "klijent_id")
    public List<TimezoneResponse> vremenskeZone = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "klijent_id")
    public List<CurrencyResponse> valute = new ArrayList<>();

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
