package org.acme;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "automobil")
@NamedQuery(name = Automobil.GET_ALL_AUTOMOBILI, query = "SELECT a FROM Automobil a")
public class Automobil {

    public static final String GET_ALL_AUTOMOBILI = "GetAllAutomobili";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auto_seq")
    @SequenceGenerator(name = "auto_seq", sequenceName = "auto_seq", allocationSize = 1)
    private Long id;

    private int godinaProizvodnje;
    private String brojSasije;
    private String registracija;
    private int klijentId;
    private int modelId;

    public Automobil() {
    }

    public Automobil(int godinaProizvodnje, String brojSasije, String registracija, int klijentId, int modelId) {
        this.godinaProizvodnje = godinaProizvodnje;
        this.brojSasije = brojSasije;
        this.registracija = registracija;
        this.klijentId = klijentId;
        this.modelId = modelId;
    }

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public int getGodinaProizvodnje() { 
        return godinaProizvodnje; 
    }
    public void setGodinaProizvodnje(int godinaProizvodnje) { 
        this.godinaProizvodnje = godinaProizvodnje; 
    }

    public String getBrojSasije() { 
        return brojSasije; 
    }
    public void setBrojSasije(String brojSasije) { 
        this.brojSasije = brojSasije; 
    }

    public String getRegistracija() { 
        return registracija; 
    }

    public void setRegistracija(String registracija) { 
        this.registracija = registracija; 
    }

    public int getKlijentId() { 
        return klijentId; 
    }
    public void setKlijentId(int klijentId) { 
        this.klijentId = klijentId; 
    }

    public int getModelId() { 
        return modelId; 
    }
    public void setModelId(int modelId) { 
        this.modelId = modelId; 
    }

    @Override
    public String toString() {
        return "Automobil [id=" + id + ", godinaProizvodnje=" + godinaProizvodnje + ", brojSasije=" + brojSasije 
                + ", registracija=" + registracija + ", klijentId=" + klijentId + ", modelId=" + modelId + "]";
    }
}