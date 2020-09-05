package com.spodfy.resource;

import com.spodfy.jwt.JwtUtil;
import com.spodfy.model.AjaxResult;
import com.spodfy.resource.config.ProductResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HealthCheckResource extends ProductResource {
    private static final Logger log = LoggerFactory.getLogger(HealthCheckResource.class);
    @Autowired
    private JwtUtil util;

    @GetMapping("/isAlive")
    public ResponseEntity<String> isAlive() {
        try {
            return ResponseEntity.ok("I'm Alive");
        } catch (Exception e) {
            log.error("Erro.", e);
        }
        return null;
    }

    @GetMapping("/me")
    public AjaxResult getMe(HttpServletResponse httpServletResponse) {
        try {
            return buildAjaxSuccessResult(util.getUsernameFromToken(getCompactToken().substring(7)));
        } catch (Exception e) {
            log.error("Erro.", e);
            httpServletResponse.setStatus(400);
            return buildAjaxErrorResult(e);
        }
    }
}

