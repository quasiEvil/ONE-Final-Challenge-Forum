package br.com.alura.forum.controller;

import br.com.alura.forum.domain.user.User;
import br.com.alura.forum.domain.user.UserRepository;
import br.com.alura.forum.domain.user.UserRoles;
import br.com.alura.forum.domain.user.dto.UserDTO;
import br.com.alura.forum.infra.security.dto.TokenJWTDTO;
import br.com.alura.forum.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO data) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Authentication authentication = manager.authenticate(authenticationToken);

            var user = (User) authentication.getPrincipal();
            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(new TokenJWTDTO(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody UserDTO signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Erro: email já cadastrado");
        }

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setRole(UserRoles.valueOf(signUpRequest.getRole()));
        user.setActive(true);

        userRepository.save(user);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO updateUserRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = optionalUser.get();
        if (updateUserRequest.getName() != null) {
            existingUser.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getEmail() != null) {
            existingUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getRole() != null) {
            existingUser.setRole(UserRoles.valueOf(updateUserRequest.getRole()));
        }
        if (updateUserRequest.getActive() != null) {
            existingUser.setActive(updateUserRequest.getActive());
        }
        if (updateUserRequest.getPassword() != null) {
            existingUser.setPassword(encoder.encode(updateUserRequest.getPassword()));
        }

        userRepository.save(existingUser);

        return ResponseEntity.ok(existingUser);
    }


    @DeleteMapping("/users/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
