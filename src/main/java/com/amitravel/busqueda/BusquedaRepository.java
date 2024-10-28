package com.amitravel.busqueda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusquedaRepository extends JpaRepository<Busqueda, Long> {

}
