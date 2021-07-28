package com.demidrolll.myphotos.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "photo")
@Getter
@Setter
@NoArgsConstructor
public class Photo extends AbstractDomain {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @SequenceGenerator(name = "photo_seq", sequenceName = "photo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_seq")
    private long id;

    @Column(name = "small_url", nullable = false, length = 255, updatable = false)
    @NotNull
    @Size(max = 255)
    private String smallUrl;

    @Column(name = "large_url", nullable = false, length = 255, updatable = false)
    @NotNull
    @Size(max = 255)
    private String largeUrl;

    @Column(name = "original_url", nullable = false, length = 255, updatable = false)
    @NotNull
    @Size(max = 255)
    private String originalUrl;

    @Column(name = "views", nullable = false)
    private long views;

    @Column(name = "downloads", nullable = false)
    private long downloads;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;
}
