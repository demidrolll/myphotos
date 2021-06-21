package com.demidrolll.myphotos.model.domain;

import com.demidrolll.myphotos.model.validation.Email;
import com.demidrolll.myphotos.model.validation.EnglishLanguage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile extends AbstractDomain {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @SequenceGenerator(name = "profile_seq", sequenceName = "profile_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_seq")
    private long id;

    @Column(name = "uid", nullable = false, length = 255, unique = true, updatable = false)
    @NotNull
    @Size(max = 255)
    private String uid;

    @Column(name = "email", nullable = false, length = 100, unique = true, updatable = false)
    @NotNull
    @Size(max = 100)
    @Email
    private String email;

    @Column(name = "avatar_url", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String avatarUrl;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime created;

    @Column(name = "first_name", nullable = false, length = 60)
    @NotNull(message = "{Profile.firstName.NotNull}")
    @Size(min = 1, max = 60, message = "Profile.firstName.Size")
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    @NotNull(message = "{Profile.lastName.NotNull}")
    @Size(min = 1, max = 60, message = "Profile.lastName.Size")
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    private String lastName;

    @Column(name = "job_title", nullable = false, length = 100)
    @NotNull(message = "{Profile.jobTitle.NotNull}")
    @Size(min = 5, max = 100, message = "Profile.jobTitle.Size")
    @EnglishLanguage
    private String jobTitle;

    @Column(name = "location", nullable = false, length = 100)
    @NotNull(message = "{Profile.location.NotNull}")
    @Size(min = 5, max = 100, message = "Profile.location.Size")
    @EnglishLanguage
    private String location;

    @Column(name = "photo_count", nullable = false)
    private int photoCount;

    @Column(name = "rating", nullable = false)
    private int rating;
}
