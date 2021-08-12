package com.demidrolll.myphotos.web.form;

import com.demidrolll.myphotos.common.annotation.group.SignUpGroup;
import com.demidrolll.myphotos.model.domain.Profile;
import com.demidrolll.myphotos.model.validation.EnglishLanguage;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileForm {

    private String uid;

    private String avatarUrl;

    @NotNull(message = "{Profile.firstName.NotNull}", groups = SignUpGroup.class)
    @Size(min = 1, max = 60, message = "Profile.firstName.Size", groups = SignUpGroup.class)
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false, groups = SignUpGroup.class)
    private String firstName;

    @NotNull(message = "{Profile.lastName.NotNull}", groups = SignUpGroup.class)
    @Size(min = 1, max = 60, message = "Profile.lastName.Size", groups = SignUpGroup.class)
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false, groups = SignUpGroup.class)
    private String lastName;

    @NotNull(message = "{Profile.jobTitle.NotNull}", groups = SignUpGroup.class)
    @Size(min = 5, max = 100, message = "Profile.jobTitle.Size", groups = SignUpGroup.class)
    @EnglishLanguage(groups = SignUpGroup.class)
    private String jobTitle;

    @NotNull(message = "{Profile.location.NotNull}", groups = SignUpGroup.class)
    @Size(min = 5, max = 100, message = "Profile.location.Size", groups = SignUpGroup.class)
    @EnglishLanguage(groups = SignUpGroup.class)
    private String location;

    @AssertTrue(message = "{ProfileForm.agree.AssertTrue}", groups = SignUpGroup.class)
    private boolean agree;

    public ProfileForm() {
    }

    public ProfileForm(Profile profile) {
        setUid(profile.getUid());
        setFirstName(profile.getFirstName());
        setLastName(profile.getLastName());
        setJobTitle(profile.getJobTitle());
        setLocation(profile.getLocation());
        setAvatarUrl(profile.getAvatarUrl());
    }

    public void copyToProfile(Profile profile) {
        profile.setFirstName(getFirstName());
        profile.setLastName(getLastName());
        profile.setJobTitle(getJobTitle());
        profile.setLocation(getLocation());
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

}
