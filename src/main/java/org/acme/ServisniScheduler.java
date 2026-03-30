package org.acme;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

@ApplicationScoped
public class ServisniScheduler {

    @Inject
    private EntityManager em;

    // Izvršava se svakih 30 sekundi (možeš staviti i "1h" za svaki sat)
    @Scheduled(every = "30s")
    void generisiIzvjestaj() {
        try {
            // Brojimo koliko ukupno ima automobila u servisu
            Long brojAutomobila = em.createQuery("SELECT COUNT(a) FROM Automobil a", Long.class).getSingleResult();
            
            // Brojimo aktivne radne naloge (gdje status nije 'ZAVRSENO')
            Long aktivniNalozi = em.createQuery("SELECT COUNT(r) FROM RadniNalog r WHERE r.status != 'ZAVRSENO'", Long.class).getSingleResult();

            System.out.println("--------------------------------------------------");
            System.out.println("SISTEMSKI IZVJEŠTAJ - " + LocalDateTime.now());
            System.out.println("Ukupno registrovanih vozila: " + brojAutomobila);
            System.out.println("Broj aktivnih popravki (naloga): " + aktivniNalozi);
            System.out.println("--------------------------------------------------");
            
        } catch (Exception e) {
            System.err.println("Greška prilikom izvršavanja schedulera: " + e.getMessage());
        }
    }
}