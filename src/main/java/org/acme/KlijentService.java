package org.acme;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@Dependent
public class KlijentService {

    @Inject
    private EntityManager em;

    @Transactional
    public Klijent createKlijent(Klijent klijent) throws KlijentException {
        if (klijent == null) {
            throw new KlijentException("Klijent nije proslijeđen.");
        }
        if (klijent.getIme() == null || klijent.getPrezime() == null
                || klijent.getIme().isBlank() || klijent.getPrezime().isBlank()) {
            throw new KlijentException("Podaci o klijentu su nepotpuni.");
        }
        return em.merge(klijent);
    }

    public List<Klijent> getAllKlijenti() throws KlijentException {
        List<Klijent> klijenti = em.createNamedQuery(Klijent.GET_ALL_KLIJENTI, Klijent.class).getResultList();
        if (klijenti.isEmpty()) {
            throw new KlijentException("Nema klijenta u sistemu.");
        }
        return klijenti;
    }

    public List<Klijent> getKlijentByName(String name) {
        return em.createNamedQuery(Klijent.GET_KLIJENT_BY_NAME, Klijent.class)
                 .setParameter("imeK", name)
                 .getResultList();
    }
    public List<Automobil> getAutomobiliByKlijentId(Long id) {
        return em.createNamedQuery(Automobil.GET_AUTOMOBILI_BY_KLIJENT_ID, Automobil.class)
                 .setParameter("idKlijenta", id)
                 .getResultList();
    }
}
