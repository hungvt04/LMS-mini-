package com.hungvt.userservice;

import com.hungvt.userservice.entity.User;
import com.hungvt.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        boolean isRun = false;
        if (isRun) {
            userRepository.deleteAll();

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@gmail.com");
            admin.setFullName("Administrator");
            admin.setGender("Nam");
            userRepository.save(admin);

            User trongHung = new User();
            trongHung.setUsername("hungvt");
            trongHung.setFullName("Trong Hung");
            trongHung.setPassword(passwordEncoder.encode("hungvt"));
            trongHung.setGender("Nam");
            trongHung.setEmail("tronghung@gmail.com");
            userRepository.save(trongHung);


            User quangHung = new User();
            quangHung.setUsername("hungvq");
            quangHung.setFullName("Quang Hung");
            quangHung.setPassword(passwordEncoder.encode("hungvq"));
            quangHung.setGender("Nam");
            quangHung.setEmail("quangHung@gmail.com");
            userRepository.save(quangHung);
        }

    }
}
