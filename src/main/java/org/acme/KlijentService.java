package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
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
}