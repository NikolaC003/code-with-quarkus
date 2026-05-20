package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/klijent")
public class KlijentResource {

    private static final java.nio.file.Path UPLOAD_DIRECTORY = java.nio.file.Path.of("uploads", "klijenti");

    @Inject
    EntityManager em;

    @Inject
    private KlijentService klijentService;

    @Inject
    @RestClient
    IpifyClient ipifyClient;

    @Inject
    @RestClient
    TimeApiClient timeApiClient;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addKlijent")           
    // @RolesAllowed("admin")
    @Transactional
    public Response addKlijent(Klijent klijent) {
        try {
            klijentService.createKlijent(klijent);
            return Response.status(Response.Status.CREATED).build();
        } catch (KlijentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getTimezoneByIP")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getTimezoneByIP(@QueryParam("userId") Long userId) {
        if (userId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Query param 'userId' je obavezan.")
                           .build();
        }

        Klijent klijent = em.find(Klijent.class, userId); 
        
        if (klijent == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Klijent sa ID-jem " + userId + " nije pronađen.")
                           .build();
        }

        try {
            String publicIp = ipifyClient.getPublicIp();

            TimezoneResponse timeData = timeApiClient.getTimezoneByIp(publicIp);

            klijent.vremenskeZone.add(timeData);
            
            em.merge(klijent);
            
            return Response.ok(klijent).build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Greška u komunikaciji sa API servisima: " + e.getMessage())
                           .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllKlijenti")
    public Response getAllKlijenti() {
        try {
            List<Klijent> klijenti = klijentService.getAllKlijenti();
            return Response.ok().entity(klijenti).build();
        } catch (KlijentException e) {
            return Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/getKlijentByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getKlijentByName(@QueryParam("name") String name) {
        List<Klijent> klijenti = klijentService.getKlijentByName(name);
        return Response.ok().entity(klijenti).build();
    }

    @GET
    @Path("/getAutomobiliByKlijentId")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutomobiliByKlijentId(@QueryParam("id") Long id) {
        List<Automobil> automobili = klijentService.getAutomobiliByKlijentId(id);
        return Response.ok().entity(automobili).build();
    }

    @POST
    @Path("/uploadFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadFile(@QueryParam("id") Long id, @BeanParam UploadFileForm form) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(message("Query param 'id' je obavezan.")).build();
        }
        if (form == null || form.filename == null || form.filename.isBlank() || form.file == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(message("Multipart polja 'filename' i 'file' su obavezna.")).build();
        }
        if (form.file.size() == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(message("Proslijeđeni fajl je prazan.")).build();
        }

        Klijent klijent = em.find(Klijent.class, id);
        if (klijent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(message("Klijent sa ID-jem " + id + " nije pronađen.")).build();
        }

        try {
            Files.createDirectories(UPLOAD_DIRECTORY);
            String safeFilename = sanitizeFilename(form.filename);
            java.nio.file.Path destination = UPLOAD_DIRECTORY.resolve(safeFilename).toAbsolutePath().normalize();
            boolean alreadyExists = Files.exists(destination);

            if (!alreadyExists) {
                Files.copy(form.file.uploadedFile(), destination);
            }

            UploadedFile uploadedFile = findOrCreateUploadedFile(destination.toString());
            if (!klijent.getUploadedFiles().contains(uploadedFile)) {
                klijent.getUploadedFiles().add(uploadedFile);
            }
            em.merge(klijent);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("message", alreadyExists
                    ? "Fajl već postoji. Klijentu je povezana postojeća putanja."
                    : "Fajl je sačuvan i povezan sa klijentom.");
            response.put("klijentId", klijent.getId());
            response.put("filename", safeFilename);
            response.put("filePath", destination.toString());
            response.put("alreadyExisted", alreadyExists);

            return Response.status(alreadyExists ? Response.Status.OK : Response.Status.CREATED)
                    .entity(response)
                    .build();
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(message("Greška pri čuvanju fajla: " + e.getMessage()))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(message(e.getMessage())).build();
        }
    }

    @GET
    @Path("/getKlijentById")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getKlijentById(@QueryParam("id") Long id) {
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Query param 'id' je obavezan.").build();
        }

        Klijent klijent = em.find(Klijent.class, id);
        if (klijent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Klijent sa ID-jem " + id + " nije pronađen.").build();
        }

        klijent.valute.size();
        klijent.getAutomobili().size();

        for (UploadedFile uploadedFile : klijent.getUploadedFiles()) {
            if (uploadedFile.getFilename() != null) {
                uploadedFile.setFile(java.nio.file.Path.of(uploadedFile.getFilename()).toFile());
            }
        }

        return Response.ok(klijent).build();
    }

    private UploadedFile findOrCreateUploadedFile(String filename) {
        List<UploadedFile> existingFiles = em.createQuery(
                        "Select u from UploadedFile u where u.filename = :filename", UploadedFile.class)
                .setParameter("filename", filename)
                .getResultList();

        if (!existingFiles.isEmpty()) {
            return existingFiles.get(0);
        }

        UploadedFile uploadedFile = new UploadedFile(filename);
        em.persist(uploadedFile);
        return uploadedFile;
    }

    private String sanitizeFilename(String filename) {
        java.nio.file.Path fileNamePath = java.nio.file.Path.of(filename).getFileName();
        if (fileNamePath == null || fileNamePath.toString().isBlank()) {
            throw new IllegalArgumentException("Ime fajla nije validno.");
        }
        return fileNamePath.toString();
    }

    private Map<String, String> message(String text) {
        return Map.of("message", text);
    }
}
