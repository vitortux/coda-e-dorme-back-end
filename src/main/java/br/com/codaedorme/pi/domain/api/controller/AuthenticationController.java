package br.com.codaedorme.pi.domain.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codaedorme.pi.domain.api.cliente.AuthenticationDTO;
import br.com.codaedorme.pi.domain.api.cliente.Cliente;
import br.com.codaedorme.pi.domain.api.cliente.ClienteRepository;
import br.com.codaedorme.pi.domain.api.cliente.LoginResponseDTO;
import br.com.codaedorme.pi.domain.api.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (UserDetails) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid Cliente cliente) {
        if (this.repository.findByEmail(cliente.getEmail()) != null)
            return ResponseEntity.badRequest().build();
        String senhaEncripitada = new BCryptPasswordEncoder().encode(cliente.getSenha());
        cliente.setSenha(senhaEncripitada);

        this.repository.save(cliente);
        return ResponseEntity.ok().build();
    }
}
