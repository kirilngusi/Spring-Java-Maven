package com.example.springbootjava.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {


    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "margima@gmail.com",
                    LocalDate.of(2000, Month.JANUARY,5),
                    21
            );

            Student alax = new Student(
                    "Alax",
                    "alax@gmail.com",
                    LocalDate.of(2002, Month.JANUARY,5),
                    19
            );

//            studentRepository.saveAll(
//                    List.of(
//                            mariam, alax
//                    )
//            );
        };
    }
}
