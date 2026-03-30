package org.acme;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = Automobil.GET_ALL_AUTOMOBILI, query = "Select a.id, a.registracija, a.brojSasije from Automobil a"),
    @NamedQuery(name = Automobil.GET_AUTOMOBILI_BY_KLIJENT_ID, query = "Select a from Automobil a where a.klijentId = :id")
})
public class Automobil {

    public static final String GET_ALL_AUTOMOBILI = "GetAllAutomobili";
    public static final String GET_AUTOMOBILI_BY_KLIJENT_ID = "GetAutomobiliByKlijentId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_seq")
    @SequenceGenerator(name = "auto_seq", sequenceName = "auto_seq", allocationSize = 1)
    private Long id;

    private int godinaProizvodnje;
    private String brojSasije;
    private String registracija;
    
    @Column(name = "klijent_id")
    private Long klijentId;

    // Tačka 1: Prva @OneToOne relacija
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "servisni_karton_id")
    private ServisniKarton servisniKarton;

    // Tačka 1: Druga @OneToOne relacija
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "radni_nalog_id")
    private RadniNalog radniNalog;

    public Automobil() {}

    // Getteri i Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getGodinaProizvodnje() { return godinaProizvodnje; }
    public void setGodinaProizvodnje(int godinaProizvodnje) { this.godinaProizvodnje = godinaProizvodnje; }
    public String getBrojSasije() { return brojSasije; }
    public void setBrojSasije(String brojSasije) { this.brojSasije = brojSasije; }
    public String getRegistracija() { return registracija; }
    public void setRegistracija(String registracija) { this.registracija = registracija; }
    public Long getKlijentId() { return klijentId; }
    public void setKlijentId(Long klijentId) { this.klijentId = klijentId; }
    public ServisniKarton getServisniKarton() { return servisniKarton; }
    public void setServisniKarton(ServisniKarton servisniKarton) { this.servisniKarton = servisniKarton; }
    public RadniNalog getRadniNalog() { return radniNalog; }
    public void setRadniNalog(RadniNalog radniNalog) { this.radniNalog = radniNalog; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Automobil that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(brojSasije, that.brojSasije);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brojSasije);
    }

    @Override
    public String toString() {
        return "Automobil{" + "id=" + id + ", registracija='" + registracija + '\'' + '}';
    }
}