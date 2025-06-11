package com.amitravel.promocion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    @Query(value = "SELECT * FROM PROMOCIONES WHERE MATCH(nombre, descripcion) AGAINST(?1)", nativeQuery = true)
    Page<Promocion> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM PROMOCIONES WHERE negocio_id = ?1 AND MATCH(nombre, descripcion) AGAINST(?2)", nativeQuery = true)
    Page<Promocion> findAllByNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Promocion> findAllByNegocioId(Long negocioId, Pageable of);
}
