package com.spodfy.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.google.common.hash.Hashing;
import com.spodfy.model.UsuarioForm;
import com.spodfy.repository.UsuarioRepository;
import com.spodfy.table.Usuario;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private CriptoService criptoService;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioForm createUsuario(UsuarioForm form) {
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Usuario.class);

        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

        Usuario user = new Usuario();
        BeanUtils.copyProperties(form, user);

        /* hash senha para salvar no banco */
        user.setDsSenha(criptoService.criptoSHA256(form.getDsSenha()));

        usuarioRepository.save(user);

        form.setIdUsuario(user.getIdUsuario());
        form.setDsSenha(null);
        return form;
    }


    public List<Usuario> findAllUsuario() {
        Iterable<Usuario> it = usuarioRepository.findAll();
        List<Usuario> result = new ArrayList<Usuario>();
        for (Usuario u : it) {
            result.add(u);
        }
        return result;
    }

}
