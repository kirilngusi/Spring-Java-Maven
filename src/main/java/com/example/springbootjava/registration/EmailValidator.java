package com.example.springbootjava.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //Regex
        return true;
    }
}
