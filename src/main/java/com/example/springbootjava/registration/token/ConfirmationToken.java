package com.example.springbootjava.registration.token;

import com.example.springbootjava.auth.Auth;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
//All about Getter Setter ArgsContractor
@Entity
public class ConfirmationToken {
    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )

    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "auth_id"
    )
    private Auth auth;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Auth auth) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.auth = auth;
    }

}
