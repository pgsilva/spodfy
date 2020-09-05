package com.spodfy.resource.api.spotify;

import com.spodfy.model.AjaxResult;
import com.spodfy.resource.config.ProductResource;
import com.spodfy.service.api.spotify.SpotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class SpotifyResource extends ProductResource {
    private static final Logger log = LoggerFactory.getLogger(SpotifyResource.class);

    @Autowired
    private SpotifyService spotifyService;

    @Value("${spotify.id.client}")
    private String clientId;

    @Value("${spotify.secret.client}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    @RequestMapping(value = "/redirect_login", method = RequestMethod.GET)
    public void redirectLoginSpotify(HttpServletResponse httpServletResponse) {
        String scopes = "user-read-private user-read-email " +
                "user-read-playback-state user-modify-playback-state " +
                "user-read-currently-playing streaming app-remote-control ";
        String projectUrl = "https://accounts.spotify.com/authorize?response_type=code";
        projectUrl += "&client_id=" + clientId;
        projectUrl += (!StringUtils.isEmpty(scopes) ? "&scope=" + URLEncoder.encode(scopes, StandardCharsets.UTF_8) : "");
        projectUrl += "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

        httpServletResponse.setHeader("Location", projectUrl);
        httpServletResponse.setStatus(302);
    }

    @GetMapping("/callback")
    public AjaxResult callbackLoginSpotify(@RequestParam String code,
                                           @RequestParam(required = false) String error,
                                           @RequestParam(required = false) String state,
                                           HttpServletResponse httpServletResponse
    ) {
        Object res;
        try {
            if (StringUtils.isEmpty(error)) {
                res = spotifyService.atualizaPermissaoUsuarioLogado(code);
            } else {
                throw new Exception("Ocorreu um erro na recuperaçao da permissao do usuario.");
            }
            return buildAjaxSuccessResult(res);
        } catch (Exception e) {
            log.error("Erro.", e);
            httpServletResponse.setStatus(400);
            return buildAjaxErrorResult(e);
        }
    }
}
