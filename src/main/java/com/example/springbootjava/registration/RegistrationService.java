package com.example.springbootjava.registration;


import com.example.springbootjava.auth.AppUserRole;
import com.example.springbootjava.auth.Auth;
import com.example.springbootjava.auth.AuthService;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AuthService authService;
    private final EmailValidator emailValidator;

    public RegistrationService(AuthService authService, EmailValidator emailValidator) {
        this.authService = authService;
        this.emailValidator = emailValidator;
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
}
