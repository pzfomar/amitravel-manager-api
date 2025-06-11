package com.amitravel.negocio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.horario.Horario;
import com.amitravel.horario.HorarioDto;
import com.amitravel.horario.HorarioRepository;
import com.amitravel.negocio.NegocioHttp.PageResponse;
import com.amitravel.negocio.NegocioHttp.Request;
import com.amitravel.negocio.NegocioHttp.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NegocioService {
    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    public ResponseEntity<NegocioHttp.Response> crear(String lang, NegocioHttp.Request request) {
        log.info("NegocioService::crear::lang:{}:request:{}", lang, request);

        Negocio nuevoNegocio = new Negocio();
        nuevoNegocio.setNombre(request.getNombre());
        nuevoNegocio.setDescripcion(request.getDescripcion());
        nuevoNegocio.setLugar(request.getLugar());
        nuevoNegocio.setMapa(request.getMapa());
        nuevoNegocio.setImagen(request.getImagen());
        nuevoNegocio.setEstatus(request.getEstatus());
        Negocio negocio = this.negocioRepository.save(nuevoNegocio);
        log.info("NegocioService::crear::save::nuevoNegocio:{}", "ok");

        if (request.getHorarios() != null && !request.getHorarios().isEmpty()) {
            List<Horario> nuevosHorarios = request.getHorarios().parallelStream()
                    .map(horarioDto -> {
                        Horario horario = new Horario();
                        horario.setAbre(horarioDto.getAbre());
                        horario.setCierre(horarioDto.getCierre());
                        horario.setDia(horarioDto.getDia());
                        horario.setNegocio(negocio);
                        return horario;
                    })
                    .collect(Collectors.toList());
            this.horarioRepository.saveAll(nuevosHorarios);
            log.info("NegocioService::crear::save::nuevosHorarios:{}", "ok");
        }

        NegocioHttp.Response response = NegocioHttp.Response.builder()
                .id(negocio.getId())
                .nombre(negocio.getNombre())
                .descripcion(negocio.getDescripcion())
                .lugar(negocio.getLugar())
                .mapa(negocio.getMapa())
                .imagen(negocio.getImagen())
                .estatus(negocio.getEstatus())
                .creacion(negocio.getCreacion().toLocalDateTime())
                .actualizacion(negocio.getActualizacion().toLocalDateTime())
                .notificaciones(null)
                .promociones(null)
                .productos(null)
                .calificaciones(null)
                .eventos(null)
                .anuncios(null)
                .build();
        log.info("NegocioService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("NegocioService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Negocio> negocio = this.negocioRepository.findById(id);
        if (negocio.isEmpty()) {
            throw new Exception("No existe el negocio");
        }

        negocio.get().setNombre(request.getNombre());
        negocio.get().setDescripcion(request.getDescripcion());
        negocio.get().setLugar(request.getLugar());
        negocio.get().setMapa(request.getMapa());
        negocio.get().setImagen(request.getImagen());
        negocio.get().setEstatus(request.getEstatus());
        this.negocioRepository.save(negocio.get());
        log.info("NegocioService::actualizar::save::nuevoNegocio:{}", "ok");

        List<Horario> horarios = this.horarioRepository.findAllByNegocio(negocio.get());
        if (horarios != null && !horarios.isEmpty()) {
            this.horarioRepository.deleteAll(horarios);
            log.info("NegocioService::actualizar::delete::horarios:{}", "ok");
        }

        if (request.getHorarios() != null && !request.getHorarios().isEmpty()) {
            List<Horario> nuevosHorarios = request.getHorarios().parallelStream()
                    .map(horarioDto -> {
                        Horario horario = new Horario();
                        horario.setAbre(horarioDto.getAbre());
                        horario.setCierre(horarioDto.getCierre());
                        horario.setDia(horarioDto.getDia());
                        horario.setNegocio(negocio.get());
                        return horario;
                    })
                    .collect(Collectors.toList());
            this.horarioRepository.saveAll(nuevosHorarios);
            log.info("NegocioService::actualizar::save::nuevosHorarios:{}", "ok");
        }

        NegocioHttp.Response response = NegocioHttp.Response.builder()
                .id(negocio.get().getId())
                .nombre(negocio.get().getNombre())
                .descripcion(negocio.get().getDescripcion())
                .lugar(negocio.get().getLugar())
                .mapa(negocio.get().getMapa())
                .imagen(negocio.get().getImagen())
                .estatus(negocio.get().getEstatus())
                .creacion(negocio.get().getCreacion().toLocalDateTime())
                .actualizacion(negocio.get().getActualizacion().toLocalDateTime())
                .notificaciones(null)
                .promociones(null)
                .productos(null)
                .calificaciones(null)
                .eventos(null)
                .anuncios(null)
                .horarios(negocio.get().getHorarios().stream()
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
        log.info("NegocioService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("NegocioService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Negocio> negocio = this.negocioRepository.findById(id);
        if (negocio.isEmpty()) {
            throw new Exception("No existe el negocio");
        }

        negocio.get().setEstatus(false);
        this.negocioRepository.save(negocio.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long id) {
        log.info("NegocioService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio, busqueda);

        Page<Negocio> negocios = null;
        if (busqueda != null) {
            negocios = this.negocioRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
        } else if (id != null) {
            Optional<Negocio> negocio = this.negocioRepository.findById(id);
            negocios = new PageImpl<Negocio>(List.of(negocio.get()), PageRequest.of(pagina, tamanio), 1L);
        } else {
            negocios = this.negocioRepository.findAll(PageRequest.of(pagina, tamanio));
        }

        PageResponse response = PageResponse.builder()
                .paginas(negocios.getTotalPages())
                .paginaSeleccionada(negocios.getNumber())
                .contenido(negocios.getContent().stream()
                        .map(negocio -> NegocioHttp.Response.builder()
                                .id(negocio.getId())
                                .nombre(negocio.getNombre())
                                .descripcion(negocio.getDescripcion())
                                .lugar(negocio.getLugar())
                                .mapa(negocio.getMapa())
                                .imagen(negocio.getImagen())
                                .estatus(negocio.getEstatus())
                                .creacion(negocio.getCreacion().toLocalDateTime())
                                .actualizacion(negocio.getActualizacion().toLocalDateTime())
                                .notificaciones(null)
                                .promociones(null)
                                .productos(null)
                                .calificaciones(null)
                                .eventos(null)
                                .anuncios(null)
                                .horarios(negocio.getHorarios().stream()
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
        log.info("NegocioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<List<Response>> obtener(String lang) {
        log.info("NegocioService::obtener::lang:{}", lang);

        List<Negocio> negocios = this.negocioRepository.findAll();

        List<NegocioHttp.Response> response = negocios.stream()
                .map(negocio -> NegocioHttp.Response.builder()
                        .id(negocio.getId())
                        .nombre(negocio.getNombre())
                        .descripcion(negocio.getDescripcion())
                        .lugar(negocio.getLugar())
                        .mapa(negocio.getMapa())
                        .imagen(negocio.getImagen())
                        .estatus(negocio.getEstatus())
                        .creacion(negocio.getCreacion().toLocalDateTime())
                        .actualizacion(negocio.getActualizacion().toLocalDateTime())
                        .notificaciones(null)
                        .promociones(null)
                        .productos(null)
                        .calificaciones(null)
                        .eventos(null)
                        .anuncios(null)
                        .horarios(negocio.getHorarios().stream()
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

        log.info("NegocioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
