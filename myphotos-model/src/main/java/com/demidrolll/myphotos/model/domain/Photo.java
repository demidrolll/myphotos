package com.demidrolll.myphotos.model.domain;

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
import java.time.LocalDateTime;

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
    private String smallUrl;

    @Column(name = "large_url", nullable = false, length = 255, updatable = false)
    private String largeUrl;

    @Column(name = "original_url", nullable = false, length = 255, updatable = false)
    private String originalUrl;

    @Column(name = "views", nullable = false)
    private long views;

    @Column(name = "downloads", nullable = false)
    private long downloads;

    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;
}
