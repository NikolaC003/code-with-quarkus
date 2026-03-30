package org.acme;


import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.exception.AutomobilException;
import me.fit.model.Automobil;
import java.util.List;

@Dependent
public class AutomobilService {

    @Inject
    private EntityManager em;

    @Transactional
    public Automobil createAutomobil(Automobil auto) throws AutomobilException {
        if (auto == null) {
            throw new AutomobilException("Automobil nije proslijeđen");
        }
        if (auto.getRegistracija().isEmpty()) {
            throw new AutomobilException("Registracija je obavezna");
        }
        return em.merge(auto);
    }

    public List<Automobil> getAllAutomobili() throws AutomobilException {
        List<Automobil> automobili = em.createNamedQuery(Automobil.GET_ALL_AUTOMOBILI, Automobil.class).getResultList();
        if (automobili.isEmpty()) {
            throw new AutomobilException("Nema automobila u bazi.");
        }
        return automobili;
    }
}