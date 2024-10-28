package com.amitravel.agenda;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.agenda.AgendaHttp.PageResponse;
import com.amitravel.agenda.AgendaHttp.Request;
import com.amitravel.agenda.AgendaHttp.Response;
import com.amitravel.evento.Evento;
import com.amitravel.evento.EventoRepository;
import com.amitravel.usuario.Usuario;
import com.amitravel.usuario.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public ResponseEntity<Response> crear(Request request) throws Exception {
        log.info("AgendaService::crear::lang:{}:request:{}", request);

        Optional<Evento> evento = this.eventoRepository.findById(request.getEventoId());

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        Agenda nuevoAgenda = new Agenda();
        nuevoAgenda.setDescripcion(request.getDescripcion());
        nuevoAgenda.setEstatus(request.getEstatus());
        if (!evento.isEmpty()) {
            nuevoAgenda.setEvento(evento.get());
        }
        nuevoAgenda.setFecha(Date.valueOf(request.getFecha()));
        nuevoAgenda.setHora(Time.valueOf(request.getHora()));
        nuevoAgenda.setNombre(request.getNombre());
        nuevoAgenda.setUsuario(usuario.get());
        Agenda agenda = this.agendaRepository.save(nuevoAgenda);
        log.info("AgendaService::crear::save::nuevoAgenda:{}", "ok");

        AgendaHttp.Response response = AgendaHttp.Response.builder()
                .id(agenda.getId())
                .usuario(null)
                .evento(null)
                .nombre(agenda.getNombre())
                .descripcion(agenda.getDescripcion())
                .fecha(agenda.getFecha().toLocalDate())
                .hora(agenda.getHora().toLocalTime())
                .estatus(agenda.getEstatus())
                .creacion(agenda.getCreacion().toLocalDateTime())
                .actualizacion(agenda.getActualizacion().toLocalDateTime())
                .build();

        log.info("AgendaService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("AgendaService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Agenda> agenda = this.agendaRepository.findById(id);
        if (agenda.isEmpty()) {
            throw new Exception("No existe la agenda");
        }

        Optional<Evento> evento = this.eventoRepository.findById(request.getEventoId());

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        agenda.get().setDescripcion(request.getDescripcion());
        agenda.get().setEstatus(request.getEstatus());
        if (!evento.isEmpty()) {
            agenda.get().setEvento(evento.get());
        }
        agenda.get().setFecha(Date.valueOf(request.getFecha()));
        agenda.get().setHora(Time.valueOf(request.getHora()));
        agenda.get().setNombre(request.getNombre());
        agenda.get().setUsuario(usuario.get());
        this.agendaRepository.save(agenda.get());
        log.info("AgendaService::actualizar::save::Agenda:{}", "ok");

        AgendaHttp.Response response = AgendaHttp.Response.builder()
                .id(agenda.get().getId())
                .usuario(null)
                .evento(null)
                .nombre(agenda.get().getNombre())
                .descripcion(agenda.get().getDescripcion())
                .fecha(agenda.get().getFecha().toLocalDate())
                .hora(agenda.get().getHora().toLocalTime())
                .estatus(agenda.get().getEstatus())
                .creacion(agenda.get().getCreacion().toLocalDateTime())
                .actualizacion(agenda.get().getActualizacion().toLocalDateTime())
                .build();

        log.info("AgendaService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("AgendaService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Agenda> agenda = this.agendaRepository.findById(id);
        if (agenda.isEmpty()) {
            throw new Exception("No existe la agenda");
        }

        agenda.get().setEstatus(false);
        this.agendaRepository.save(agenda.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio) {
        log.info("AgendaService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Agenda> agendas = this.agendaRepository.findAll(PageRequest.of(pagina, tamanio));

        PageResponse response = PageResponse.builder()
                .paginas(agendas.getTotalPages())
                .paginaSeleccionada(agendas.getNumber())
                .contenido(agendas.getContent().stream()
                        .map(agenda -> AgendaHttp.Response.builder()
                                .id(agenda.getId())
                                .usuario(null)
                                .evento(null)
                                .nombre(agenda.getNombre())
                                .descripcion(agenda.getDescripcion())
                                .fecha(agenda.getFecha().toLocalDate())
                                .hora(agenda.getHora().toLocalTime())
                                .estatus(agenda.getEstatus())
                                .creacion(agenda.getCreacion().toLocalDateTime())
                                .actualizacion(agenda.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
