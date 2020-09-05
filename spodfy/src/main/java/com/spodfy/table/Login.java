package com.spodfy.table;

import lombok.*;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlogin;
    private String dsuser;
    private String dssenha;
    private String dsemail;
    private LocalDate dtultimoacesso;
    private LocalDate dtcriacao;
    private Integer nrperfil;
    private String nmusuario;


}
