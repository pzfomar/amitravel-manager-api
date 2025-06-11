package com.amitravel.notificacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query(value = "SELECT * FROM NOTIFICACIONES WHERE MATCH(nombre, descripcion, tipo) AGAINST(?1)", nativeQuery = true)
    Page<Notificacion> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM NOTIFICACIONES WHERE negocio_id = ?1 AND MATCH(nombre, descripcion, tipo) AGAINST(?2)", nativeQuery = true)
    Page<Notificacion> findAllByNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Notificacion> findAllByNegocioId(Long negocioId, Pageable of);
}
