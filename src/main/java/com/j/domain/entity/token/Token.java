package com.j.domain.entity.token;

import com.j.domain.entity.BaseEntity;
import com.j.domain.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@Table(name = "tb_token")
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime accessExpireAt;

    private LocalDateTime refreshExpireAt;

    @ManyToOne
    private User user;

    public boolean isAccessExpired() {
        return accessExpireAt.isBefore(LocalDateTime.now());
    }

    public boolean isRefreshExpired() {
        return refreshExpireAt.isBefore(LocalDateTime.now());
    }

    /**
     * 替换token
     *
     * @param newToken 新token
     */
    public void replace(Token newToken) {
        this.accessToken = newToken.getAccessToken();
        this.refreshToken = newToken.getRefreshToken();
        this.accessExpireAt = newToken.getAccessExpireAt();
        this.refreshExpireAt = newToken.getRefreshExpireAt();
    }
}
