package com.spodfy.service;

import com.spodfy.model.LoginForm;
import com.spodfy.model.TokenSpotifyApi;
import com.spodfy.repository.LoginRepository;
import com.spodfy.repository.LoginSpotifyRepository;
import com.spodfy.table.Login;
import com.spodfy.table.LoginSpotify;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


@Service
public class LoginService {

    @Autowired
    private CriptoService criptoService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginSpotifyRepository loginSpotifyRepository;

    @Transactional(rollbackFor = Exception.class)
    public LoginForm createUsuario(LoginForm form) throws Exception {
        if (loginRepository.findByDsemail(form.getDsemail()) == null) {

            Login user = new Login();
            BeanUtils.copyProperties(form, user);

            /* hash senha para salvar no banco */
            user.setDssenha(criptoService.criptoSHA256(form.getDssenha()));

            user.setNrperfil(new Integer("1"));
            user.setDtcriacao(LocalDateTime.now());
            loginRepository.save(user);

            form.setIdusuario(user.getIdlogin());
            form.setDssenha(null);
            return form;
        } else {
            throw new Exception("E-mail já cadastrado");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void salvarUsuarioSpotify(Login login, Map userSpot, TokenSpotifyApi token) {
        if (login == null) {
            login = new Login();
            login.setDtcriacao(LocalDateTime.now());
            login.setDtultimoacesso(LocalDateTime.now());
            login.setNrperfil(new Integer("1"));
            login.setDsemail(userSpot.get("email").toString());
            login.setNmusuario(userSpot.get("display_name").toString());
            login.setDsuser(userSpot.get("email").toString());

            loginRepository.save(login);
            atualizarTokenUsuario(login, userSpot, token);

        } else {
            login.setDtultimoacesso(LocalDateTime.now());

            loginRepository.save(login);
            atualizarTokenUsuario(login, userSpot, token);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    private void atualizarTokenUsuario(Login login, Map userSpot, TokenSpotifyApi token) {
        LoginSpotify loginSpotify = loginSpotifyRepository.findByLoginIdlogin(login.getIdlogin());
        if (loginSpotify == null) {
            loginSpotify = new LoginSpotify();
            loginSpotify.setLogin(login);
        }
        loginSpotify.setDtrequisicao(LocalDateTime.now());
        loginSpotify.setAccesstoken(token.getAccess_token());
        loginSpotify.setRefreshtoken(token.getRefresh_token());
        loginSpotify.setExpiresin(token.getExpires_in());

        loginSpotifyRepository.save(loginSpotify);
    }

}
