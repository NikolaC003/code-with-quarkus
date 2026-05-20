package org.acme;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import java.io.File;
import java.util.Objects;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploaded_file_seq")
    @SequenceGenerator(name = "uploaded_file_seq", sequenceName = "uploaded_file_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String filename;

    @Transient
    private File file;

    public UploadedFile() {
    }

    public UploadedFile(String filename) {
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadedFile that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(filename, that.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filename);
    }
}
