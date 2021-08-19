package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.ejb.model.ProfileUidGenerator;
import com.demidrolll.myphotos.ejb.service.ProfileUidService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@ProfileUidGenerator(category = ProfileUidGenerator.Category.SECONDARY)
public class SecondaryProfileUidService implements ProfileUidService {

    @Override
    public List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName) {
        return List.of(
                String.format("%s-%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                String.format("%s.%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                String.format("%s%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                englishLastName.toLowerCase(),
                englishFirstName.toLowerCase()
        );
    }
}
