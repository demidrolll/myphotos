package com.demidrolll.myphotos.web.model;

import com.demidrolll.myphotos.common.model.AbstractMimeTypeImageResource;

import javax.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class PartImageResource extends AbstractMimeTypeImageResource {

    private final Part part;

    public PartImageResource(Part part) {
        this.part = Objects.requireNonNull(part);
    }

    @Override
    protected String getContentType() {
        return part.getContentType();
    }

    @Override
    protected void copyContent() throws Exception {
        Files.copy(part.getInputStream(), getTempPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), part);
    }

    @Override
    protected void deleteTempResources() throws Exception {
        part.delete();
    }
}
