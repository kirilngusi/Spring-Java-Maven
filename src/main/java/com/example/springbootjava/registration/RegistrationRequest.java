package com.example.springbootjava.registration;


import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class RegistrationRequest {
    private final String  firstName;
    private final String  lastName;
    private final String  email;
    private final String  password;
}
