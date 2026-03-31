
package org.acme;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Mehanicar.GET_ALL_MEHANICARI, query = "Select m.id, m.ime, m.prezime, m.specijalnost from Mehanicar m")
@NamedQuery(name = Mehanicar.GET_MEHANICAR_BY_NAME, query = "Select m from Mehanicar m where m.ime = :imeM")
public class Mehanicar {

    public static final String GET_ALL_MEHANICARI = "GetAllMehanicari";
    public static final String GET_MEHANICAR_BY_NAME = "GetMehanicarByName";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meh_seq")
    @SequenceGenerator(name = "meh_seq", sequenceName = "meh_seq", allocationSize = 1)
    private Long id;

    private String ime;
    private String prezime;
    private String specijalnost;

    public Mehanicar() {
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "mehanicar_id")
    private List<RadniNalog> nalozi = new ArrayList<>();

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
    public String getSpecijalnost() { 
        return specijalnost; 
        }
    public void setSpecijalnost(String specijalnost) { 
        this.specijalnost = specijalnost; 
        }
    public List<RadniNalog> getNalozi() { 
        return nalozi; 
        }
    public void setNalozi(List<RadniNalog> nalozi) { 
        this.nalozi = nalozi; 
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mehanicar that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(ime, that.ime) && Objects.equals(prezime, that.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime);
    }

    @Override
    public String toString() {
        return "Mehanicar{" + "id=" + id + ", ime='" + ime + '\'' + ", prezime='" + prezime + '\'' + '}';
    }
}

