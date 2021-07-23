package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.ejb.service.FileNameGeneratorService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class FileNameGeneratorServiceImpl implements FileNameGeneratorService {

    @Override
    public String generateUniqueFileName() {
        return UUID.randomUUID().toString() + ".jpg";
    }
}
