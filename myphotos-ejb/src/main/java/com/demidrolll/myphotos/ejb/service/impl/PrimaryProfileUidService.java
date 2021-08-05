package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.ejb.model.ProfileUidGenerator;
import com.demidrolll.myphotos.ejb.service.ProfileUidService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
@ProfileUidGenerator(category = ProfileUidGenerator.Category.PRIMARY)
public class PrimaryProfileUidService implements ProfileUidService {

    @Override
    public List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName) {
        return List.of(
                String.format("%s-%s", englishFirstName, englishLastName).toLowerCase(),
                String.format("%s.%s", englishFirstName, englishLastName).toLowerCase(),
                String.format("%s%s", englishFirstName, englishLastName).toLowerCase()
        );
    }
}
