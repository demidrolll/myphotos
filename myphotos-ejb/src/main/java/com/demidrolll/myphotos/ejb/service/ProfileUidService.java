package com.demidrolll.myphotos.ejb.service;

import java.util.List;

public interface ProfileUidService {

    List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName);
}
