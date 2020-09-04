package com.spodfy.repository;

import com.spodfy.table.Usuario;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {
}

