package com.example.springbootjava.auth;

import com.example.springbootjava.registration.token.ConfirmationToken;
import com.example.springbootjava.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    protected static final Logger logger = LogManager.getLogger();

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return authRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(Auth auth) {
        //if is value -> true and otherwise
        boolean userExists =  authRepository.findByEmail(auth.getEmail()).isPresent();

        if (userExists) {
            logger.info("Hello World!");
            throw new IllegalStateException("Email Is Already Taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(
                auth.getPassword()
        );

        auth.setPassword(encodedPassword);

        authRepository.save(auth);

        String token = UUID.randomUUID().toString();
        //TODO: SEND CONFIRMATION TOKEN
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                auth
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        //TODO: SEND EMAIL
        return token;
    }

    public int enableAppUser(String email) {
        return authRepository.enableAppUser(email);
    }
}
