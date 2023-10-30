package com.kirilldikun.ishop.authentication;

import com.kirilldikun.ishop.configuration.JwtService;
import com.kirilldikun.ishop.dto.PersonDTO;
import com.kirilldikun.ishop.dto.UserDTO;
import com.kirilldikun.ishop.entity.User;
import com.kirilldikun.ishop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        UserDTO userDTO = UserDTO.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role("USER")
                .person(
                        PersonDTO.builder()
                                .firstName(registerRequest.getFirstName())
                                .lastName(registerRequest.getLastName())
                                .build()
                )
                .build();
        userService.save(userDTO);
        String jwt = jwtService.generateToken(userService.mapToUser(userDTO));
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userService.findByEmail(authenticationRequest.getEmail());
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
