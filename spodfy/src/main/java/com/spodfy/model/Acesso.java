package com.spodfy.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Acesso {
    private String nmusuario;
    private String dsemail;
    private LocalDateTime dtacesso;
    private String tokenAcesso;
    private String urlImage;
}
