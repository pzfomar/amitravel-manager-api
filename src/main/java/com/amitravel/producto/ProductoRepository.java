package com.amitravel.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    @Query(value = "SELECT * FROM PRODUCTOS WHERE MATCH(nombre, tipo, descripcion) AGAINST(?1)", nativeQuery = true)
    Page<Producto> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM PRODUCTOS WHERE negocio_id = ?1 AND MATCH(nombre, tipo, descripcion) AGAINST(?2)", nativeQuery = true)
    Page<Producto> findAllByNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Producto> findAllByNegocioId(Long negocioId, Pageable of);
}
