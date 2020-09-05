package com.spodfy.table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="loginspotify")
public class LoginSpotify implements Serializable {
    private static final long serialVersionUID = -4790443289293960919L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idloginspotify;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlogin")
    private Login login;

    private LocalDateTime dtrequisicao;
    private String accesstoken;
    private String refreshtoken;


    @Column(name="expiresin")
    private Long expiresin;


}
