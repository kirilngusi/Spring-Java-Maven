package com.example.springbootjava.registration;


import com.example.springbootjava.auth.AppUserRole;
import com.example.springbootjava.auth.Auth;
import com.example.springbootjava.auth.AuthService;
import com.example.springbootjava.registration.token.ConfirmationToken;
import com.example.springbootjava.registration.token.ConfirmationTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final AuthService authService;
    private final EmailValidator emailValidator;

    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationService(AuthService authService, EmailValidator emailValidator, ConfirmationTokenService confirmationTokenService) {
        this.authService = authService;
        this.emailValidator = emailValidator;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email Not Valid");
        }
        return authService.signUpUser(
                new Auth(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token Not Found"));

        if(confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        authService.enableAppUser(
                confirmationToken.getAuth().getEmail());

        return "confirm";
    }
}
