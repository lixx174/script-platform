package com.j.domain.token;

import com.j.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@Table(name = "tb_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String accessToken;

    private String refreshToken;

    private Instant accessExpireAt;

    private Instant refreshExpireAt;

    @ManyToOne
    private User user;

    public boolean isAccessExpired() {
        return accessExpireAt.isBefore(Instant.now());
    }

    public boolean isRefreshExpired() {
        return refreshExpireAt.isBefore(Instant.now());
    }
}
