package com.example.chapter15.repository;

import com.example.chapter15.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void insertDummyClients() {
        String[][] dummies = {
                {"demo-app",   "demo-secret",   "Demo Application"},
                {"web-client", "web-secret",    "Web Frontend"},
                {"mobile-app", "mobile-secret", "Mobile Application"},
                {"admin-tool", "admin-secret",  "Internal Admin Tool"}
        };

        for (String[] d : dummies) {
            Client client = new Client();
            client.setClientId(d[0]);
            client.setClientSecret(passwordEncoder.encode(d[1]));
            client.setName(d[2]);
            client.setActive(true);
            clientRepository.save(client);
            System.out.println("등록됨: " + d[0] + " / 평문 secret: " + d[1]);
        }
    }
}
