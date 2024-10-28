package com.amitravel.promocion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;
import com.amitravel.promocion.PromocionHttp.PageResponse;
import com.amitravel.promocion.PromocionHttp.Request;
import com.amitravel.promocion.PromocionHttp.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PromocionService {

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private PromocionRepository promocionRepository;

    public ResponseEntity<Response> crear(String lang, Request request) throws Exception {
        log.info("PromocionService::crear::lang:{}:request:{}", lang, request);

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        Promocion nuevoPromocion = new Promocion();
        nuevoPromocion.setDescripcion(request.getDescripcion());
        nuevoPromocion.setEstatus(request.getEstatus());
        nuevoPromocion.setImagen(request.getImagen());
        nuevoPromocion.setUrl(request.getUrl());
        nuevoPromocion.setNegocio(negocio.get());
        nuevoPromocion.setNombre(request.getNombre());
        Promocion promocion = this.promocionRepository.save(nuevoPromocion);
        log.info("PromocionService::crear::save::nuevoPromocion:{}", "ok");

        PromocionHttp.Response response = PromocionHttp.Response.builder()
                .id(promocion.getId())
                .negocio(null)
                .nombre(promocion.getNombre())
                .descripcion(promocion.getDescripcion())
                .imagen(promocion.getImagen())
                .url(promocion.getUrl())
                .estatus(promocion.getEstatus())
                .creacion(promocion.getCreacion().toLocalDateTime())
                .actualizacion(promocion.getActualizacion().toLocalDateTime())
                .build();
        log.info("PromocionService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("PromocionService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Promocion> promocion = this.promocionRepository.findById(id);
        if (promocion.isEmpty()) {
            throw new Exception("No existe la promocion");
        }

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        promocion.get().setDescripcion(request.getDescripcion());
        promocion.get().setEstatus(request.getEstatus());
        promocion.get().setImagen(request.getImagen());
        promocion.get().setUrl(request.getUrl());
        promocion.get().setNegocio(negocio.get());
        promocion.get().setNombre(request.getNombre());
        this.promocionRepository.save(promocion.get());
        log.info("PromocionService::actualizar::save::promocion:{}", "ok");

        PromocionHttp.Response response = PromocionHttp.Response.builder()
                .id(promocion.get().getId())
                .negocio(null)
                .nombre(promocion.get().getNombre())
                .descripcion(promocion.get().getDescripcion())
                .imagen(promocion.get().getImagen())
                .url(promocion.get().getUrl())
                .estatus(promocion.get().getEstatus())
                .creacion(promocion.get().getCreacion().toLocalDateTime())
                .actualizacion(promocion.get().getActualizacion().toLocalDateTime())
                .build();
        log.info("PromocionService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("PromocionService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Promocion> promocion = this.promocionRepository.findById(id);
        if (promocion.isEmpty()) {
            throw new Exception("No existe la promocion");
        }

        promocion.get().setEstatus(false);
        this.promocionRepository.save(promocion.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long negocioId) {
        log.info("PromocionService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio,
                busqueda);

        Page<Promocion> promociones = null;
        if (negocioId != null) {
            if (busqueda != null) {
                promociones = this.promocionRepository.findAllByNegocioIdAndMatch(negocioId, busqueda,
                        PageRequest.of(pagina, tamanio));
            } else {
                promociones = this.promocionRepository.findAllByNegocioId(negocioId, PageRequest.of(pagina, tamanio));
            }
        } else {
            if (busqueda != null) {
                promociones = this.promocionRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
            } else {
                promociones = this.promocionRepository.findAll(PageRequest.of(pagina, tamanio));
            }
        }

        PageResponse response = PageResponse.builder()
                .paginas(promociones.getTotalPages())
                .paginaSeleccionada(promociones.getNumber())
                .contenido(promociones.getContent().stream()
                        .map(promocion -> PromocionHttp.Response.builder()
                                .id(promocion.getId())
                                .negocio(NegocioHttp.Response.builder()
                                        .id(promocion.getNegocio().getId())
                                        .nombre(promocion.getNegocio().getNombre())
                                        .descripcion(promocion.getNegocio().getDescripcion())
                                        .imagen(promocion.getNegocio().getImagen())
                                        .estatus(promocion.getNegocio().getEstatus())
                                        .creacion(promocion.getNegocio().getCreacion().toLocalDateTime())
                                        .actualizacion(promocion.getNegocio().getActualizacion().toLocalDateTime())
                                        .build())
                                .nombre(promocion.getNombre())
                                .descripcion(promocion.getDescripcion())
                                .imagen(promocion.getImagen())
                                .url(promocion.getUrl())
                                .estatus(promocion.getEstatus())
                                .creacion(promocion.getCreacion().toLocalDateTime())
                                .actualizacion(promocion.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("PromocionService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<Response>> obtener(String lang) {
        log.info("PromocionService::obtener::lang:{}", lang);

        List<Promocion> promociones = this.promocionRepository.findAll();

        List<PromocionHttp.Response> response = promociones.stream()
                .map(promocion -> PromocionHttp.Response.builder()
                        .id(promocion.getId())
                        .negocio(NegocioHttp.Response.builder()
                                .id(promocion.getNegocio().getId())
                                .nombre(promocion.getNegocio().getNombre())
                                .descripcion(promocion.getNegocio().getDescripcion())
                                .imagen(promocion.getNegocio().getImagen())
                                .estatus(promocion.getNegocio().getEstatus())
                                .creacion(promocion.getNegocio().getCreacion().toLocalDateTime())
                                .actualizacion(promocion.getNegocio().getActualizacion().toLocalDateTime())
                                .build())
                        .nombre(promocion.getNombre())
                        .descripcion(promocion.getDescripcion())
                        .imagen(promocion.getImagen())
                        .url(promocion.getUrl())
                        .estatus(promocion.getEstatus())
                        .creacion(promocion.getCreacion().toLocalDateTime())
                        .actualizacion(promocion.getActualizacion().toLocalDateTime())
                        .build())
                .collect(Collectors.toList());

        log.info("PromocionService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
