package com.coopbank.onboarding.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_challenges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String otp;

    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;
}