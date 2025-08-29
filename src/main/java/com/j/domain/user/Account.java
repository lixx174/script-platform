package com.j.domain.user;

import com.j.domain.primitive.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jinx
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tb_Account")
public class Account {

    @Id
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String reason;

    @OneToOne
    private User user;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isForbidden() {
        return status == Status.FORBIDDEN;
    }
}
