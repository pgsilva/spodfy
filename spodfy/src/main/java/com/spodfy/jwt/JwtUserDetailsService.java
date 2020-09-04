package com.spodfy.jwt;

import java.util.ArrayList;
import java.util.Optional;

import com.amazonaws.util.StringUtils;
import com.spodfy.repository.UsuarioRepository;
import com.spodfy.service.CriptoService;
import com.spodfy.table.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsPasswordService, UserDetailsService {

    @Autowired
    CriptoService criptoService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails updatePassword(UserDetails username, String pass) throws UsernameNotFoundException {
        if (StringUtils.isNullOrEmpty(username.getUsername())) {
            String criptoRequest = criptoService.criptoSHA256(pass);
            Optional<Usuario> usuarioOpt = Optional.empty();


            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                Boolean result = criptoService.compareCriptoSHA256(criptoRequest, usuario.getDsSenha());

                if (Boolean.TRUE.equals(result))
                    return new User(usuario.getNmUsuario(), usuario.getDsUser(), new ArrayList<>());

            } else {
                throw new UsernameNotFoundException("User not found with username: " + username.getUsername());
            }
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username.getUsername());
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isNullOrEmpty(username)) {
            return new User(username, "xxx",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}