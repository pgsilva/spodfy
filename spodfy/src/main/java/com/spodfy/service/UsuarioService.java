package com.spodfy.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.spodfy.model.UsuarioForm;
import com.spodfy.repository.UsuarioRepository;
import com.spodfy.table.Usuario;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void createUsuario(UsuarioForm form) {
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Usuario.class);

        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));

        TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);

        Usuario user = new Usuario();
        BeanUtils.copyProperties(form, user);
        usuarioRepository.save(user);
    }
}