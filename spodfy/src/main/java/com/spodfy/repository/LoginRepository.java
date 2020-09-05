package com.spodfy.repository;

import com.spodfy.table.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByDsuser(String username);

    Login findByDsemail(String email);
}

