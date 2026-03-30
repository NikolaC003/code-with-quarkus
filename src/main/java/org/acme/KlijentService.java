package org.acme;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.exception.KlijentException;
import me.fit.model.Automobil;
import me.fit.model.Klijent;
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
        if (klijent.getIme().isEmpty() || klijent.getPrezime().isEmpty()) {
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

/*import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class KlijentService {

    @Inject
    EntityManager entityManager;

    public List<Klijent> dohvatiSve() {
        return entityManager.createNamedQuery(Klijent.GET_ALL_KLIJENTI, Klijent.class).getResultList();
    }

    @Transactional
    public Klijent sacuvaj(Klijent klijent) {
        entityManager.persist(klijent);
        return klijent;
    }
}*/