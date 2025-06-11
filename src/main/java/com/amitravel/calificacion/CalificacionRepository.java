package com.amitravel.calificacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    @Query(value = "SELECT * FROM CALIFICACIONES WHERE comentario LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    Page<Calificacion> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM CALIFICACIONES C INNER JOIN EVENTOS E ON E.id = C.evento_id WHERE E.negocio_id = ?1 AND C.comentario LIKE CONCAT('%', ?2, '%')", nativeQuery = true)
    Page<Calificacion> findAllByEventoNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Calificacion> findAllByEventoNegocioId(Long negocioId, Pageable of);
}
