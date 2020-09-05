package com.spodfy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Acesso {
    private String nmusuario;
    private String dsEmail;
    private LocalDateTime dtAcesso;
    private String tokenAcesso;
}
