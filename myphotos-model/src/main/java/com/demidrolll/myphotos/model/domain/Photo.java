package com.demidrolll.myphotos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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

    @Column(name = "created", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date created;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;
}
