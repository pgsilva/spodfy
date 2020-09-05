package com.spodfy.jwt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.spodfy.repository.LoginRepository;
import com.spodfy.service.CriptoService;
import com.spodfy.table.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    CriptoService criptoService;

    @Autowired
    LoginRepository loginRepository;

    public Boolean verifyPassword(String username, String pass) throws UsernameNotFoundException {
        String criptoRequest = criptoService.criptoSHA256(pass);
        Optional<Login> usuarioOpt = loginRepository.findByDsuser(username);
        if (usuarioOpt.isPresent()) {
            Login usuario = usuarioOpt.get();
            Boolean result = criptoService.compareCriptoSHA256(criptoRequest, usuario.getDssenha());

            if (Boolean.TRUE.equals(result)) {
                usuario.setDtultimoacesso(LocalDate.now());
                loginRepository.save(usuario);
                return Boolean.TRUE;
            }

        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return Boolean.FALSE;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> loginOptional = loginRepository.findByDsuser(username);
        if (loginOptional.isPresent()) {
            return new User(loginOptional.get().getDsuser(), loginOptional.get().getDssenha(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}