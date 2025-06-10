package com.clinic.inv_api.Controller;


import com.clinic.inv_api.Auth.AuthDTO;
import com.clinic.inv_api.Auth.TokenService;
import com.clinic.inv_api.Auth.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.AuthResponseDTO> login(@RequestBody AuthDTO.AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        var user = (UserModel) authentication.getPrincipal();
        var jwtToken = tokenService.generateToken(user);
        return ResponseEntity.ok(new AuthDTO.AuthResponseDTO(jwtToken));
    }
}