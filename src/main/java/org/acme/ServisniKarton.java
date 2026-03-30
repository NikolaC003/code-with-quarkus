package org.acme;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQuery(name = ServisniKarton.GET_ALL_KARTONI, query = "Select s.id, s.opisKvara, s.datumOtvaranja from ServisniKarton s")
public class ServisniKarton implements Serializable {

    public static final String GET_ALL_KARTONI = "GetAllKartoni";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "karton_seq")
    @SequenceGenerator(name = "karton_seq", sequenceName = "karton_seq", allocationSize = 1)
    private Long id;

    private String opisKvara;
    private String datumOtvaranja;
    private String napomena;

    public ServisniKarton() {
    }

    public ServisniKarton(Long id, String opisKvara, String datumOtvaranja) {
        this.id = id;
        this.opisKvara = opisKvara;
        this.datumOtvaranja = datumOtvaranja;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOpisKvara() { return opisKvara; }
    public void setOpisKvara(String opisKvara) { this.opisKvara = opisKvara; }
    public String getDatumOtvaranja() { return datumOtvaranja; }
    public void setDatumOtvaranja(String datumOtvaranja) { this.datumOtvaranja = datumOtvaranja; }
    public String getNapomena() { return napomena; }
    public void setNapomena(String napomena) { this.napomena = napomena; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServisniKarton that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(opisKvara, that.opisKvara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, opisKvara);
    }

    @Override
    public String toString() {
        return "ServisniKarton{" + "id=" + id + ", opisKvara='" + opisKvara + '\'' + '}';
    }
}