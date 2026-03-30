package org.acme;


import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.exception.RadniNalogException;
import me.fit.model.RadniNalog;
import java.util.List;

@Dependent
public class RadniNalogService {

    @Inject
    private EntityManager em;

    @Transactional
    public RadniNalog createRadniNalog(RadniNalog nalog) throws RadniNalogException {
        if (nalog == null) {
            throw new RadniNalogException("Radni nalog nije proslijeđen.");
        }
        if (nalog.getBrojNaloga() == null || nalog.getBrojNaloga().isEmpty()) {
            throw new RadniNalogException("Broj naloga ne smije biti prazan.");
        }
        return em.merge(nalog);
    }

    public List<RadniNalog> getAllNalozi() throws RadniNalogException {
        List<RadniNalog> nalozi = em.createNamedQuery(RadniNalog.GET_ALL_NALOZI, RadniNalog.class).getResultList();
        if (nalozi.isEmpty()) {
            throw new RadniNalogException("Nema radnih naloga u bazi.");
        }
        return nalozi;
    }
}