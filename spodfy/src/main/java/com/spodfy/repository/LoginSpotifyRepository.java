package com.spodfy.repository;


import com.spodfy.table.LoginSpotify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginSpotifyRepository extends JpaRepository<LoginSpotify, Long> {

    LoginSpotify findByLoginIdlogin(Long idlogin);
}

