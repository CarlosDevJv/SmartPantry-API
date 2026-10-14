package dev.carlos.smartpantry.service;

import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.entities.dto.UserRegister;
import dev.carlos.smartpantry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void insert(UserRegister user){
        if (userRepository.findByEmail(user.email()).isPresent()){throw new IllegalArgumentException("Usuário já cadastrado");}
        User newUser = new User();
        newUser.setNome(user.nome());
        newUser.setEmail(user.email());
        newUser.setSenha(passwordEncoder.encode(user.senha()));
        userRepository.save(newUser);
    }
}
