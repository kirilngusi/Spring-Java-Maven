package com.example.springbootjava.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
            throw new IllegalStateException("Email Is Already Taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(
                auth.getPassword()
        );

        auth.setPassword(encodedPassword);

        authRepository.save(auth);

        //TODO: SEND CONFIRMATION TOKEN


        return "Here";
    }
}
