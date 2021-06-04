package com.demidrolll.myphotos.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_token")
@Getter
@Setter
@NoArgsConstructor
public class AccessToken {

    @Id
    @Column(name = "token", nullable = false, length = 512, unique = true)
    private String token;

    @Column(name = "created", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;
}
