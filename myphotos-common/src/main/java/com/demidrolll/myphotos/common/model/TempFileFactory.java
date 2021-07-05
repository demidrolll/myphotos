package com.demidrolll.myphotos.common.model;

import com.demidrolll.myphotos.exception.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempFileFactory {

    public static Path createTempFile(String extension) {
        String uniqueFileName = String.format("%s.%s", UUID.randomUUID(), extension);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path tempFilePath = Paths.get(tempDirectoryPath, uniqueFileName);
        try {
            return Files.createFile(tempFilePath);
        } catch (IOException e) {
            throw new ApplicationException(String.format("Can't create temp file %s", tempFilePath), e);
        }
    }

    public static void deleteTempFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            Logger.getLogger(TempFileFactory.class.getSimpleName()).log(Level.WARNING, String.format("Can't delete temp file: %s", path), e);
        }
    }
}
