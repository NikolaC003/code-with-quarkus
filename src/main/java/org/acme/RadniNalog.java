package org.acme;


import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NamedQueries({
    @NamedQuery(name = RadniNalog.GET_ALL_NALOZI, query = "Select r.id, r.brojNaloga, r.status from RadniNalog r"),
    @NamedQuery(name = RadniNalog.GET_ALL_NALOZI_FOR_MEHANICAR_ID, query = "Select r from RadniNalog r where r.mehanicarId = :id")
})
public class RadniNalog implements Serializable {

    public static final String GET_ALL_NALOZI = "GetAllNalozi";
    public static final String GET_ALL_NALOZI_FOR_MEHANICAR_ID = "GetAllNaloziForMehanicarId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nalog_seq")
    @SequenceGenerator(name = "nalog_seq", sequenceName = "nalog_seq", allocationSize = 1)
    private Long id;

    private String brojNaloga;
    private String status;
    private double cijenaRada;

    @Column(name = "mehanicar_id")
    private Long mehanicarId;

    public RadniNalog() {}

    public RadniNalog(Long id, String brojNaloga, String status) {
        this.id = id;
        this.brojNaloga = brojNaloga;
        this.status = status;
    }

    // Getteri i Setteri
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBrojNaloga() { return brojNaloga; }
    public void setBrojNaloga(String brojNaloga) { this.brojNaloga = brojNaloga; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getCijenaRada() { return cijenaRada; }
    public void setCijenaRada(double cijenaRada) { this.cijenaRada = cijenaRada; }
    public Long getMehanicarId() { return mehanicarId; }
    public void setMehanicarId(Long mehanicarId) { this.mehanicarId = mehanicarId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadniNalog that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(brojNaloga, that.brojNaloga);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brojNaloga);
    }

    @Override
    public String toString() {
        return "RadniNalog{" + "id=" + id + ", brojNaloga='" + brojNaloga + '\'' + ", status='" + status + '\'' + '}';
    }
}