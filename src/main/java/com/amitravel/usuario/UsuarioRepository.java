package com.amitravel.usuario;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM USUARIOS WHERE MATCH(apodo, rol) AGAINST(?1)", nativeQuery = true)
    Page<Usuario> findAllByMatch(String busqueda, Pageable of);

    Optional<Usuario> findByApodo(String apodo);
}
