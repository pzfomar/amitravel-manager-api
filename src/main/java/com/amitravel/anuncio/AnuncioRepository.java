package com.amitravel.anuncio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    @Query(value = "SELECT * FROM ANUNCIOS WHERE MATCH(nombre, descripcion, url) AGAINST(?1)", nativeQuery = true)
    Page<Anuncio> findAllByMatch(String busqueda, Pageable of);

    @Query(value = "SELECT * FROM ANUNCIOS WHERE negocio_id = ?1 AND MATCH(nombre, descripcion, url) AGAINST(?2)", nativeQuery = true)
    Page<Anuncio> findAllByNegocioIdAndMatch(Long negocioId, String busqueda, Pageable of);

    Page<Anuncio> findAllByNegocioId(Long negocioId, Pageable of);
}
