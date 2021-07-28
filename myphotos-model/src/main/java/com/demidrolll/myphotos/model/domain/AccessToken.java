package com.demidrolll.myphotos.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
