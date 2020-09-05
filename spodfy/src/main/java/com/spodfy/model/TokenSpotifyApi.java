package com.spodfy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenSpotifyApi {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String scope;

}
