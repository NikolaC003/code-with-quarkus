package org.acme;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import me.fit.exception.MehanicarException;
import me.fit.model.Mehanicar;
import me.fit.model.RadniNalog;
import java.util.List;

@Dependent
public class MehanicarService {

    @Inject
    private EntityManager em;

    @Transactional
    public Mehanicar createMehanicar(Mehanicar mehanicar) throws MehanicarException {
        if (mehanicar == null) {
            throw new MehanicarException("Mehaničar nije proslijeđen");
        }
        if (mehanicar.getIme().isEmpty()) {
            throw new MehanicarException("Ime mehaničara je prazno");
        }
        return em.merge(mehanicar);
    }

    public List<Mehanicar> getAllMehanicari() throws MehanicarException {
        List<Mehanicar> mehanicari = em.createNamedQuery(Mehanicar.GET_ALL_MEHANICARI, Mehanicar.class).getResultList();
        if (mehanicari.isEmpty()) {
            throw new MehanicarException("Nema mehaničara u bazi.");
        }
        return mehanicari;
    }

    public List<RadniNalog> getNaloziByMehanicarId(Long id) {
        // Koristimo NamedQuery iz klase RadniNalog
        return em.createNamedQuery(RadniNalog.GET_ALL_NALOZI_FOR_MEHANICAR_ID, RadniNalog.class)
                 .setParameter("id", id)
                 .getResultList();
    }
}