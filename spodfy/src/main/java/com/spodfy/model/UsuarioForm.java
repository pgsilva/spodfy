package com.spodfy.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioForm {
    private String nmUsuario;
    private String dsUser;
    private String dsEmail;
    private String dsSenha;

}
