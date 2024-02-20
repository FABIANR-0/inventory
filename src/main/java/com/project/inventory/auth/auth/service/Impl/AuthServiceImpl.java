package com.project.inventory.auth.auth.service.Impl;

import com.project.inventory.auth.auth.dto.LoginRequest;
import com.project.inventory.auth.auth.dto.LoginResponse;
import com.project.inventory.auth.auth.service.AuthService;
import com.project.inventory.auth.security.jwt.JwtTokenProvider;
import com.project.inventory.auth.user.entity.User;
import com.project.inventory.auth.user.service.UserServiceShared;
import com.project.inventory.common.exception.service.AuthenticationFailedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceShared userServiceShared;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserServiceShared userServiceShared, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userServiceShared = userServiceShared;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userServiceShared.getUserByUserName(request.getUsername().toLowerCase(Locale.ROOT).replace(" ", ""));
        if (user == null)
            throw new AuthenticationFailedException("Credenciales incorrectas");

        if (user.isLocked())
            throw new AuthenticationFailedException("Usuario bloqueado");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            if (user.getLoginAttempts() >= 2) {
                this.updateLockedUser(user);
            } else {
                this.updateLoginAttempts(user);
            }
        }
        user.updateLoginAttempts(0);
        user.addCodeVerification(this.generateCodeVerification());

        userServiceShared.saveUser(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authenticationToken);
        return new LoginResponse(jwtTokenProvider.generateToken(user.getUserName()));
    }

    private void updateLoginAttempts(User user) {
        user.updateLoginAttempts(user.getLoginAttempts() + 1);
        userServiceShared.saveUser(user);
        throw new AuthenticationFailedException(
                String.format("Credenciales incorrectas, Queda%s %d intento%s",
                        user.getLoginAttempts() != 1 ? "n" : "",
                        3 - user.getLoginAttempts(),
                        user.getLoginAttempts() != 1 ? "s" : ""));
    }

    private void updateLockedUser(User user) {
        user.updateLocked(true);
        userServiceShared.saveUser(user);
        throw new AuthenticationFailedException("Usuario bloqueado");
    }

    private String generateCodeVerification() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(999999);
        return String.format("%06d", code);
    }
}
