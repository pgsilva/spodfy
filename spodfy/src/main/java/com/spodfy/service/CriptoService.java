package com.spodfy.service;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author milesmorales
 * Classe utilitaria para manipulacao de hashs no sistema
 * */
@Service
public class CriptoService {


    public String criptoSHA256(String param) {
        String sha256hex = Hashing.sha256()
                .hashString(param, StandardCharsets.UTF_8)
                .toString();

        return sha256hex;
    }

    public Boolean compareCriptoSHA256(String param1, String param2) {
        /*Necessario ambos parametros serem criptografados*/
        if (param1.equals(param2))
            return Boolean.TRUE;

        return Boolean.FALSE;
    }
}
