package com.spodfy.service;

import com.spodfy.model.LoginForm;
import com.spodfy.repository.LoginRepository;
import com.spodfy.table.Login;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class LoginService {

    @Autowired
    private CriptoService criptoService;

    @Autowired
    private LoginRepository loginRepository;

    @Transactional(rollbackFor = Exception.class)
    public LoginForm createUsuario(LoginForm form) throws Exception {
        if (loginRepository.findByDsemail(form.getDsemail()) == null) {

            Login user = new Login();
            BeanUtils.copyProperties(form, user);

            /* hash senha para salvar no banco */
            user.setDssenha(criptoService.criptoSHA256(form.getDssenha()));

            user.setNrperfil(new Integer("1"));
            user.setDtcriacao(LocalDate.now());
            loginRepository.save(user);

            form.setIdusuario(user.getIdlogin());
            form.setDssenha(null);
            return form;
        } else {
            throw new Exception("E-mail já cadastrado");
        }

    }

}
