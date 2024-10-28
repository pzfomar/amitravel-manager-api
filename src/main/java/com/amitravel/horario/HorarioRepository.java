package com.amitravel.horario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitravel.evento.Evento;
import com.amitravel.negocio.Negocio;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findAllByNegocio(Negocio negocio);

    List<Horario> findAllByEvento(Evento evento);
}
