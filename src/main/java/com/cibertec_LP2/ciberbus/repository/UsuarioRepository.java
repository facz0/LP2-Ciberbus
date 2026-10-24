package com.cibertec_LP2.ciberbus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec_LP2.ciberbus.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNroDocumento(String nroDocumento);

}
