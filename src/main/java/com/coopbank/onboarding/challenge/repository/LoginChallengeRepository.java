package com.coopbank.onboarding.challenge.repository;

import com.coopbank.onboarding.challenge.entity.LoginChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginChallengeRepository
        extends JpaRepository<LoginChallenge, Long> {

    Optional<LoginChallenge>
    findFirstByPhoneNumberOrderByCreatedAtDesc(
            String phoneNumber
    );
}