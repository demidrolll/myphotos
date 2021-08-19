package com.demidrolll.myphotos.ejb.service.impl;

import com.demidrolll.myphotos.ejb.model.ProfileUidGenerator;
import com.demidrolll.myphotos.ejb.service.ProfileUidService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProfileUidServiceManager {

    @Inject
    @Any
    private Instance<ProfileUidService> profileUidServices;

    public List<String> getProfileUidCandidates(String englishFirstName, String englishLastName) {
        List<String> result = new ArrayList<>();
        addCandidates(new PrimaryProfileUidGenerator(), result, englishFirstName, englishLastName);
        addCandidates(new SecondaryProfileUidGenerator(), result, englishFirstName, englishLastName);
        return Collections.unmodifiableList(result);
    }

    public String getDefaultUid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void addCandidates(AnnotationLiteral<ProfileUidGenerator> selector, List<String> result, String englishFirstName, String englishLastName) {
        Instance<ProfileUidService> services = profileUidServices.select(selector);
        for (ProfileUidService service : services) {
            result.addAll(service.generateProfileUidCandidates(englishFirstName, englishLastName));
        }
    }

    private static class PrimaryProfileUidGenerator extends AnnotationLiteral<ProfileUidGenerator> implements ProfileUidGenerator {
        @Override
        public Category category() {
            return Category.PRIMARY;
        }
    }

    private static class SecondaryProfileUidGenerator extends AnnotationLiteral<ProfileUidGenerator> implements ProfileUidGenerator {
        @Override
        public Category category() {
            return Category.SECONDARY;
        }
    }
}
