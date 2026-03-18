package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped 
public class AutomobilService {

    @Inject
    EntityManager entityManager;

    public List<Automobil> dohvatiSveAutomobile() {
        return entityManager.createNamedQuery(Automobil.GET_ALL_AUTOMOBILI, Automobil.class).getResultList();
    }

    @Transactional
    public Automobil kreirajNoviAuto(Automobil auto) {
        if (auto.getGodinaProizvodnje() < 1886) {
            throw new IllegalArgumentException("Godina proizvodnje nije validna!");
        }
        
        entityManager.persist(auto);
        return auto;
    }
}