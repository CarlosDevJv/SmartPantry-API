package dev.carlos.smartpantry.controller;

import dev.carlos.smartpantry.entities.User;
import dev.carlos.smartpantry.entities.dto.UserLogin;
import dev.carlos.smartpantry.entities.dto.UserRegister;
import dev.carlos.smartpantry.repository.UserRepository;
import dev.carlos.smartpantry.security.TokenProvider;
import dev.carlos.smartpantry.service.IngredienteService;
import dev.carlos.smartpantry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    IngredienteService ingredienteService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity cadastrar(@RequestBody UserRegister userRegister){
        if (userRepository.findByEmail(userRegister.email()).isPresent()){throw new IllegalArgumentException("Usuário já cadastrado");}
        userService.insert(userRegister);
        String jwt = tokenProvider.generateToken(userRepository.findByEmail(userRegister.email()).get());
        return ResponseEntity.ok().body(jwt);

    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLogin userLogin){
        var authToken = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(userLogin.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não cadastrado"));

        String jwt = tokenProvider.generateToken(user);
        return ResponseEntity.ok().body(jwt);
    }


}
