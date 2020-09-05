package com.spodfy.table;

import lombok.*;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Login implements Serializable {

    private static final long serialVersionUID = 3312002695007237541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idlogin;
    private String dsuser;
    private String dssenha;
    private String dsemail;
    private LocalDateTime dtultimoacesso;
    private LocalDateTime dtcriacao;
    private Integer nrperfil;
    private String nmusuario;


}
