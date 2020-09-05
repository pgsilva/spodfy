package com.spodfy.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {
    private Long idusuario;
    private String nmusuario;
    private String dsuser;
    private String dsemail;
    private String dssenha;
}
