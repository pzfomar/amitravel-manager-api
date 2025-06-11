package com.amitravel.servicio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class ServicioService {
    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    public ResponseEntity<ServicioHttp.Response> crear(String lang, ServicioHttp.Request request) throws Exception {
        log.info("ServicioService::crear::lang::request::{}", lang, request);

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setDescripcion(request.getDescripcion());
        nuevoServicio.setEstatus(request.getEstatus());
        nuevoServicio.setImagen(request.getImagen());
        nuevoServicio.setLugar(request.getLugar());
        nuevoServicio.setMapa(request.getMapa());
        nuevoServicio.setNegocio(negocio.get());
        nuevoServicio.setNombre(request.getNombre());
        nuevoServicio.setTipo(request.getTipo());
        Servicio servicio = this.servicioRepository.save(nuevoServicio);
        log.info("ServicioService::crear::save::nuevoServicio{}", "ok");

        ServicioHttp.Response response = ServicioHttp.Response.builder()
                .id(servicio.getId())
                .negocio(null)
                .nombre(servicio.getNombre())
                .descripcion(servicio.getDescripcion())
                .tipo(servicio.getTipo())
                .lugar(servicio.getLugar())
                .mapa(servicio.getMapa())
                .imagen(servicio.getImagen())
                .estatus(servicio.getEstatus())
                .creacion(servicio.getCreacion().toLocalDateTime())
                .actualizacion(servicio.getActualizacion().toLocalDateTime())
                .agendas(null)
                .calificaciones(null)
                .build();
        log.info("ServicioService::crear::responce::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    public ResponseEntity<ServicioHttp.Response> actualizar(String lang, Long id, ServicioHttp.Request request)
            throws Exception {
        log.info("ServicioService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Servicio> servicio = this.servicioRepository.findById(id);
        if (servicio.isEmpty()) {
            throw new Exception("No existe el servicio");
        }

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        servicio.get().setDescripcion(request.getDescripcion());
        servicio.get().setEstatus(request.getEstatus());
        servicio.get().setImagen(request.getImagen());
        servicio.get().setLugar(request.getLugar());
        servicio.get().setMapa(request.getMapa());
        servicio.get().setNegocio(negocio.get());
        servicio.get().setNombre(request.getNombre());
        servicio.get().setTipo(request.getTipo());
        this.servicioRepository.save(servicio.get());
        log.info("EventoService::actualizar::save::evento{}", "ok");

        ServicioHttp.Response response = ServicioHttp.Response.builder()
                .id(servicio.get().getId())
                .negocio(null)
                .nombre(servicio.get().getNombre())
                .descripcion(servicio.get().getDescripcion())
                .tipo(servicio.get().getTipo())
                .lugar(servicio.get().getLugar())
                .mapa(servicio.get().getMapa())
                .imagen(servicio.get().getImagen())
                .estatus(servicio.get().getEstatus())
                .creacion(servicio.get().getCreacion().toLocalDateTime())
                .actualizacion(servicio.get().getActualizacion().toLocalDateTime())
                .agendas(null)
                .calificaciones(null)
                .build();
        log.info("ServicioService::actualizar::responce::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ServicioHttp.Response> eliminar(String lang, Long id) throws Exception {
        log.info("ServicioService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Servicio> servicio = this.servicioRepository.findById(id);
        if (servicio.isEmpty()) {
            throw new Exception("No existe el servicio");
        }

        servicio.get().setEstatus(false);
        this.servicioRepository.save(servicio.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<ServicioHttp.PageResponse> obtener(String lang, Integer pagina, Integer tamanio,
            String busqueda) {
        log.info("ServicioService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio, busqueda);

        Page<Servicio> servicios = null;
        if (busqueda != null) {
            servicios = this.servicioRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
        } else {
            servicios = this.servicioRepository.findAll(PageRequest.of(pagina, tamanio));
        }

        ServicioHttp.PageResponse response = ServicioHttp.PageResponse.builder()
                .paginas(servicios.getTotalPages())
                .paginaSeleccionada(servicios.getNumber())
                .contenido(servicios.getContent().stream()
                        .map(servicio -> ServicioHttp.Response.builder()
                                .id(servicio.getId())
                                .negocio(NegocioHttp.Response.builder()
                                        .id(servicio.getNegocio().getId())
                                        .nombre(servicio.getNegocio().getNombre())
                                        .descripcion(servicio.getNegocio().getDescripcion())
                                        .lugar(servicio.getNegocio().getLugar())
                                        .mapa(servicio.getNegocio().getMapa())
                                        .imagen(servicio.getNegocio().getImagen())
                                        .estatus(servicio.getNegocio().getEstatus())
                                        .creacion(servicio.getNegocio().getCreacion().toLocalDateTime())
                                        .actualizacion(servicio.getNegocio().getActualizacion().toLocalDateTime())
                                        .build())
                                .nombre(servicio.getNombre())
                                .descripcion(servicio.getDescripcion())
                                .tipo(servicio.getTipo())
                                .lugar(servicio.getLugar())
                                .mapa(servicio.getMapa())
                                .imagen(servicio.getImagen())
                                .estatus(servicio.getEstatus())
                                .creacion(servicio.getCreacion().toLocalDateTime())
                                .actualizacion(servicio.getActualizacion().toLocalDateTime())
                                .agendas(null)
                                .calificaciones(null)
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("ServicioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<List<ServicioHttp.Response>> obtener(String lang) {
        log.info("ServicioService::obtener::lang:{}", lang);

        List<Servicio> servicios = this.servicioRepository.findAll();

        List<ServicioHttp.Response> response = servicios.stream()
                .map(servicio -> ServicioHttp.Response.builder()
                        .id(servicio.getId())
                        .negocio(NegocioHttp.Response.builder()
                                .id(servicio.getNegocio().getId())
                                .nombre(servicio.getNegocio().getNombre())
                                .descripcion(servicio.getNegocio().getDescripcion())
                                .lugar(servicio.getNegocio().getLugar())
                                .mapa(servicio.getNegocio().getMapa())
                                .imagen(servicio.getNegocio().getImagen())
                                .estatus(servicio.getNegocio().getEstatus())
                                .creacion(servicio.getNegocio().getCreacion().toLocalDateTime())
                                .actualizacion(servicio.getNegocio().getActualizacion().toLocalDateTime())
                                .build())
                        .nombre(servicio.getNombre())
                        .descripcion(servicio.getDescripcion())
                        .tipo(servicio.getTipo())
                        .lugar(servicio.getLugar())
                        .mapa(servicio.getMapa())
                        .imagen(servicio.getImagen())
                        .estatus(servicio.getEstatus())
                        .creacion(servicio.getCreacion().toLocalDateTime())
                        .actualizacion(servicio.getActualizacion().toLocalDateTime())
                        .agendas(null)
                        .calificaciones(null)
                        .build())
                .collect(Collectors.toList());

        log.info("ServicioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
