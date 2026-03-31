package org.acme;


import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Dependent
public class ServisniKartonService {

    @Inject
    private EntityManager em;

    @Transactional
    public ServisniKarton createKarton(ServisniKarton karton) throws ServisniKartonException {
        if (karton == null) {
            throw new ServisniKartonException("Karton nije proslijeđen.");
        }
        if (karton.getOpisKvara() == null || karton.getOpisKvara().isEmpty()) {
            throw new ServisniKartonException("Opis kvara ne smije biti prazan.");
        }
        return em.merge(karton);
    }

    public List<ServisniKarton> getAllKartoni() throws ServisniKartonException {
        List<ServisniKarton> kartoni = em.createNamedQuery(ServisniKarton.GET_ALL_KARTONI, ServisniKarton.class).getResultList();
        if (kartoni.isEmpty()) {
            throw new ServisniKartonException("Nema servisnih kartona u bazi.");
        }
        return kartoni;
    }
}
