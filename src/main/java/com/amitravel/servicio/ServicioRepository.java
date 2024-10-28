package com.amitravel.servicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    @Query(value = "SELECT * FROM SERVICIOS WHERE MATCH(nombre, descripcion, tipo, lugar) AGAINST(?1)", nativeQuery = true)
    Page<Servicio> findAllByMatch(String busqueda, Pageable of);
}
