package org.acme;

import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

public class UploadFileForm {

    @RestForm
    public String filename;

    @RestForm("file")
    public FileUpload file;
}
