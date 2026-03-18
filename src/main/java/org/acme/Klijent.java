package org.acme;

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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getBrTelefona() { return brTelefona; }
    public void setBrTelefona(String brTelefona) { this.brTelefona = brTelefona; }

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
}