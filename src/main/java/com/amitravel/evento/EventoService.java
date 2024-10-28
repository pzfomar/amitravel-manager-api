package com.amitravel.evento;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.evento.EventoHttp.PageResponse;
import com.amitravel.evento.EventoHttp.Request;
import com.amitravel.evento.EventoHttp.Response;
import com.amitravel.horario.Horario;
import com.amitravel.horario.HorarioDto;
import com.amitravel.horario.HorarioRepository;
import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventoService {
    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    public ResponseEntity<EventoHttp.Response> crear(String lang, EventoHttp.Request request) throws Exception {
        log.info("EventoService::crear::lang::request::{}", lang, request);

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        Evento nuevoEvento = new Evento();
        nuevoEvento.setDescripcion(request.getDescripcion());
        nuevoEvento.setEstatus(request.getEstatus());
        nuevoEvento.setImagen(request.getImagen());
        nuevoEvento.setLugar(request.getLugar());
        nuevoEvento.setMapa(request.getMapa());
        nuevoEvento.setNegocio(negocio.get());
        nuevoEvento.setNombre(request.getNombre());
        nuevoEvento.setTipo(request.getTipo());
        Evento evento = this.eventoRepository.save(nuevoEvento);
        log.info("EventoService::crear::save::nuevoEvento{}", "ok");

        if (request.getHorarios() != null && !request.getHorarios().isEmpty()) {
            List<Horario> nuevosHorarios = request.getHorarios().parallelStream()
                    .map(horarioDto -> {
                        Horario horario = new Horario();
                        horario.setAbre(horarioDto.getAbre());
                        horario.setCierre(horarioDto.getCierre());
                        horario.setDia(horarioDto.getDia());
                        horario.setEvento(evento);
                        return horario;
                    })
                    .collect(Collectors.toList());
            this.horarioRepository.saveAll(nuevosHorarios);
            log.info("EventoService::crear::save::nuevosHorarios:{}", "ok");
        }

        EventoHttp.Response response = EventoHttp.Response.builder()
                .id(evento.getId())
                .negocio(null)
                .nombre(evento.getNombre())
                .descripcion(evento.getDescripcion())
                .tipo(evento.getTipo())
                .lugar(evento.getLugar())
                .mapa(evento.getMapa())
                .imagen(evento.getImagen())
                .estatus(evento.getEstatus())
                .creacion(evento.getCreacion().toLocalDateTime())
                .actualizacion(evento.getActualizacion().toLocalDateTime())
                .agendas(null)
                .calificaciones(null)
                .build();
        log.info("EventoService::crear::responce::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("EventoService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Evento> evento = this.eventoRepository.findById(id);
        if (evento.isEmpty()) {
            throw new Exception("No existe el evento");
        }

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        evento.get().setDescripcion(request.getDescripcion());
        evento.get().setEstatus(request.getEstatus());
        evento.get().setImagen(request.getImagen());
        evento.get().setLugar(request.getLugar());
        evento.get().setMapa(request.getMapa());
        evento.get().setNegocio(negocio.get());
        evento.get().setNombre(request.getNombre());
        evento.get().setTipo(request.getTipo());
        this.eventoRepository.save(evento.get());
        log.info("EventoService::actualizar::save::evento{}", "ok");

        List<Horario> horarios = this.horarioRepository.findAllByEvento(evento.get());
        if (horarios != null && !horarios.isEmpty()) {
            this.horarioRepository.deleteAll(horarios);
            log.info("EventoService::actualizar::delete::horarios:{}", "ok");
        }

        if (request.getHorarios() != null && !request.getHorarios().isEmpty()) {
            List<Horario> nuevosHorarios = request.getHorarios().parallelStream()
                    .map(horarioDto -> {
                        Horario horario = new Horario();
                        horario.setAbre(horarioDto.getAbre());
                        horario.setCierre(horarioDto.getCierre());
                        horario.setDia(horarioDto.getDia());
                        horario.setEvento(evento.get());
                        return horario;
                    })
                    .collect(Collectors.toList());
            this.horarioRepository.saveAll(nuevosHorarios);
            log.info("EventoService::actualizar::save::nuevosHorarios:{}", "ok");
        }

        EventoHttp.Response response = EventoHttp.Response.builder()
                .id(evento.get().getId())
                .negocio(null)
                .nombre(evento.get().getNombre())
                .descripcion(evento.get().getDescripcion())
                .tipo(evento.get().getTipo())
                .lugar(evento.get().getLugar())
                .mapa(evento.get().getMapa())
                .imagen(evento.get().getImagen())
                .estatus(evento.get().getEstatus())
                .creacion(evento.get().getCreacion().toLocalDateTime())
                .actualizacion(evento.get().getActualizacion().toLocalDateTime())
                .agendas(null)
                .calificaciones(null)
                .horarios(evento.get().getHorarios().stream()
                        .map(horario -> {
                            HorarioDto horarioDto = new HorarioDto();
                            horarioDto.setAbre(horario.getAbre());
                            horarioDto.setCierre(horario.getCierre());
                            horarioDto.setDia(horario.getDia());
                            horarioDto.setId(horario.getId());
                            return horarioDto;
                        })
                        .collect(Collectors.toList()))
                .build();
        log.info("EventoService::actualizar::responce::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("EventoService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Evento> evento = this.eventoRepository.findById(id);
        if (evento.isEmpty()) {
            throw new Exception("No existe el evento");
        }

        evento.get().setEstatus(false);
        this.eventoRepository.save(evento.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long negocioId) {
        log.info("EventoService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio, busqueda);

        Page<Evento> eventos = null;
        if (negocioId != null) {
            if (busqueda != null) {
                eventos = this.eventoRepository.findAllByNegocioIdAndMatch(negocioId, busqueda,
                        PageRequest.of(pagina, tamanio));
            } else {
                eventos = this.eventoRepository.findAllByNegocioId(negocioId, PageRequest.of(pagina, tamanio));
            }
        } else {
            if (busqueda != null) {
                eventos = this.eventoRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
            } else {
                eventos = this.eventoRepository.findAll(PageRequest.of(pagina, tamanio));
            }
        }

        PageResponse response = PageResponse.builder()
                .paginas(eventos.getTotalPages())
                .paginaSeleccionada(eventos.getNumber())
                .contenido(eventos.getContent().stream()
                        .map(evento -> EventoHttp.Response.builder()
                                .id(evento.getId())
                                .negocio(NegocioHttp.Response.builder()
                                        .id(evento.getNegocio().getId())
                                        .nombre(evento.getNegocio().getNombre())
                                        .descripcion(evento.getNegocio().getDescripcion())
                                        .lugar(evento.getNegocio().getLugar())
                                        .mapa(evento.getNegocio().getMapa())
                                        .imagen(evento.getNegocio().getImagen())
                                        .estatus(evento.getNegocio().getEstatus())
                                        .creacion(evento.getNegocio().getCreacion().toLocalDateTime())
                                        .actualizacion(evento.getNegocio().getActualizacion().toLocalDateTime())
                                        .build())
                                .nombre(evento.getNombre())
                                .descripcion(evento.getDescripcion())
                                .tipo(evento.getTipo())
                                .lugar(evento.getLugar())
                                .mapa(evento.getMapa())
                                .imagen(evento.getImagen())
                                .estatus(evento.getEstatus())
                                .creacion(evento.getCreacion().toLocalDateTime())
                                .actualizacion(evento.getActualizacion().toLocalDateTime())
                                .agendas(null)
                                .calificaciones(null)
                                .horarios(evento.getHorarios().stream()
                                        .map(horario -> {
                                            HorarioDto horarioDto = new HorarioDto();
                                            horarioDto.setAbre(horario.getAbre());
                                            horarioDto.setCierre(horario.getCierre());
                                            horarioDto.setDia(horario.getDia());
                                            horarioDto.setId(horario.getId());
                                            return horarioDto;
                                        })
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("EventoService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<Response>> obtener(String lang) {

        log.info("EventoService::obtener::lang:{}", lang);

        List<Evento> eventos = this.eventoRepository.findAll();

        List<EventoHttp.Response> response = eventos.stream()
                .map(evento -> EventoHttp.Response.builder()
                        .id(evento.getId())
                        .negocio(NegocioHttp.Response.builder()
                                .id(evento.getNegocio().getId())
                                .nombre(evento.getNegocio().getNombre())
                                .descripcion(evento.getNegocio().getDescripcion())
                                .lugar(evento.getNegocio().getLugar())
                                .mapa(evento.getNegocio().getMapa())
                                .imagen(evento.getNegocio().getImagen())
                                .estatus(evento.getNegocio().getEstatus())
                                .creacion(evento.getNegocio().getCreacion().toLocalDateTime())
                                .actualizacion(evento.getNegocio().getActualizacion().toLocalDateTime())
                                .build())
                        .nombre(evento.getNombre())
                        .descripcion(evento.getDescripcion())
                        .tipo(evento.getTipo())
                        .lugar(evento.getLugar())
                        .mapa(evento.getMapa())
                        .imagen(evento.getImagen())
                        .estatus(evento.getEstatus())
                        .creacion(evento.getCreacion().toLocalDateTime())
                        .actualizacion(evento.getActualizacion().toLocalDateTime())
                        .agendas(null)
                        .calificaciones(null)
                        .horarios(evento.getHorarios().stream()
                                .map(horario -> {
                                    HorarioDto horarioDto = new HorarioDto();
                                    horarioDto.setAbre(horario.getAbre());
                                    horarioDto.setCierre(horario.getCierre());
                                    horarioDto.setDia(horario.getDia());
                                    horarioDto.setId(horario.getId());
                                    return horarioDto;
                                })
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        log.info("EventoService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
