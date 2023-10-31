package com.barberia.barberia.repository;


import com.barberia.barberia.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}