package com.spodfy.resource;

import com.spodfy.jwt.JwtRequest;
import com.spodfy.jwt.JwtResponse;
import com.spodfy.jwt.JwtUserDetailsService;
import com.spodfy.jwt.JwtUtil;
import com.spodfy.model.AjaxResult;
import com.spodfy.model.UsuarioForm;
import com.spodfy.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin
public class AuthResource extends ProductResource {
    private static final Logger log = LoggerFactory.getLogger(AuthResource.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .updatePassword(new User(authenticationRequest.getUsername(), null, new ArrayList<>()), authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AjaxResult createRegisterUser(@RequestBody UsuarioForm form) throws Exception {
        try {
            return buildAjaxSuccessResult(usuarioService.createUsuario(form));
        } catch (Exception e) {
            log.error("Erro.", e);
            return buildAjaxErrorResult(e);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}