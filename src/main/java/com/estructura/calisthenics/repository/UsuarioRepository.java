package com.estructura.calisthenics.repository;

import com.estructura.calisthenics.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends  JpaRepository<Usuario, Long>{
}