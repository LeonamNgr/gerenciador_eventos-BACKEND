package com.leonam.gerenciador_eventos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.leonam.gerenciador_eventos.entity.Administrador;
import com.leonam.gerenciador_eventos.repository.AdministradorRepository;

@Component
public class AdministradorInitializer implements CommandLineRunner {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.nome}")
    private String nome;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.senha}")
    private String senha;

    public AdministradorInitializer(
            AdministradorRepository administradorRepository,
            PasswordEncoder passwordEncoder) {

        this.administradorRepository = administradorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (administradorRepository.count() == 0) {

            Administrador administrador = new Administrador(
                    nome,
                    email,
                    passwordEncoder.encode(senha));

            administradorRepository.save(administrador);

            System.out.println(
                    "Administrador padrão criado com sucesso.");
        }
    }
}