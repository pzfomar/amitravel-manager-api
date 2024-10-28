package com.amitravel.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query(value = "SELECT * FROM EVENTOS WHERE MATCH(nombre, descripcion, tipo, lugar) AGAINST(?1)", nativeQuery = true)
    Page<Evento> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM EVENTOS WHERE negocio_id = ?1 AND MATCH(nombre, descripcion, tipo, lugar) AGAINST(?2)", nativeQuery = true)
    Page<Evento> findAllByNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Evento> findAllByNegocioId(Long negocioId, Pageable of);
}
