package com.spodfy.service.api.spotify;

import com.google.gson.Gson;
import com.spodfy.jwt.JwtUtil;
import com.spodfy.model.Acesso;
import com.spodfy.model.TokenSpotifyApi;
import com.spodfy.repository.LoginRepository;
import com.spodfy.resource.api.spotify.SpotifyResource;
import com.spodfy.service.LoginService;
import com.spodfy.table.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpotifyService {
    private static final Logger log = LoggerFactory.getLogger(SpotifyService.class);

    @Autowired
    Environment env;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    /**
     * @apiNote Esse meteodo recebe o code de permissao que a api retornou
     * e faz a requisicao para o token do oauth inclusive salva no banco
     */
    public Acesso atualizaPermissaoUsuarioLogado(String code) throws Exception {
        Acesso res = new Acesso();

        String body = preparaBodyRequisicaoToken(code);
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(env.getProperty("spotify.api.token")))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", prepararHeaderCredentialsID())
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpStatus.BAD_REQUEST.value())
            throw new Exception("Response Spotify Api: " + response.body().toString());

        TokenSpotifyApi token = gson.fromJson(response.body().toString(), TokenSpotifyApi.class);

        /*
         *Agora que temos o token ja esta tudo pronto, vamos salvar no banco, mas p isso
         * infelizmente vamos ter que chamar outro endpoint para pegar o email desse usuario xD
         *
         * */
        recuperarInfoUsuarioAutenticado(token, res);
        return res;
    }

    private void recuperarInfoUsuarioAutenticado(TokenSpotifyApi token, Acesso acesso) throws Exception {
        /*vamos criar ou atualizar o usuario que permitiu o acesso*/
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(env.getProperty("spotify.api.v1.me")))
                .header("Authorization", new String("Bearer " + token.getAccess_token()))
                .GET().build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map usuarioMap = gson.fromJson(response.body().toString(), Map.class);

        acesso.setNmusuario(usuarioMap.get("display_name").toString());
        acesso.setDsEmail(usuarioMap.get("email").toString());
        acesso.setDtAcesso(LocalDateTime.now());
        acesso.setTokenAcesso(jwtUtil.generateToken(usuarioMap.get("email").toString()));

        Login login = loginRepository.findByDsemail(usuarioMap.get("email").toString());
        loginService.salvarUsuarioSpotify(login, usuarioMap, token);
    }

    private String prepararHeaderCredentialsID() {
        String credentials = env.getProperty("spotify.id.client") + ":" + env.getProperty("spotify.secret.client");
        String header = "Basic ";
        header += Base64.getEncoder().encodeToString(credentials.getBytes());
        return header;
    }

    private String preparaBodyRequisicaoToken(String code) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("code", code);
        parameters.put("redirect_uri", env.getProperty("spotify.redirect.uri"));
        String res = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        return res;
    }
}
