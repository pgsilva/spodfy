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
    private String dsEmail;
    private LocalDateTime dtAcesso;
    private String tokenAcesso;
}
