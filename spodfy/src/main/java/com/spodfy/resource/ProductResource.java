package com.spodfy.resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductResource extends BaseResource {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String MEDIA_TYPE_HEADER = "media-type";

    @Autowired
    private HttpServletRequest request;

    protected String getCompactToken() {
        String compactToken = request.getHeader(TOKEN_HEADER);
        return compactToken;
    }

    protected String getMediaType() {
        String dsMediaType = request.getHeader(MEDIA_TYPE_HEADER);
        return dsMediaType;
    }

    protected String getNrIpAdress() {
        String ipAdress = request.getRemoteAddr();
        return ipAdress;
    }

    protected String getDsUserAgent() {
        String dsUserAgent = request.getHeader("User-Agent");
        return dsUserAgent;
    }

}
