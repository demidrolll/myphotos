package com.demidrolll.myphotos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_token")
@Getter
@Setter
@NoArgsConstructor
public class AccessToken {

    @Id
    @Column(name = "token", nullable = false, length = 512, unique = true)
    @NotNull
    private String token;

    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;

    @Column(name = "created", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime created;
}
