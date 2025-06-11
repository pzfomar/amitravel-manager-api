package com.amitravel.negocio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    @Query(value = "SELECT * FROM NEGOCIOS WHERE MATCH(nombre) AGAINST(?1)", nativeQuery = true)
    Page<Negocio> findAllByMatch(String busqueda, Pageable of);
}
