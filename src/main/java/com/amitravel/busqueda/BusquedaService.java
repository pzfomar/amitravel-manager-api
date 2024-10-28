package com.amitravel.busqueda;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.busqueda.BusquedaHttp.PageResponse;
import com.amitravel.busqueda.BusquedaHttp.Request;
import com.amitravel.busqueda.BusquedaHttp.Response;
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
public class BusquedaService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BusquedaRepository busquedaRepository;

    public ResponseEntity<BusquedaHttp.Response> crear(BusquedaHttp.Request request) throws Exception {
        log.info("BusquedaService::crear::lang:{}:request:{}", request);

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        Busqueda nuevoBusqueda = new Busqueda();
        nuevoBusqueda.setContenido(request.getContenido());
        nuevoBusqueda.setEstatus(request.getEstatus());
        nuevoBusqueda.setUsuario(usuario.get());
        nuevoBusqueda.setVisitada(Timestamp.valueOf(request.getVisitada()));
        Busqueda busqueda = this.busquedaRepository.save(nuevoBusqueda);
        log.info("BusquedaService::crear::save::nuevoBusqueda:{}", "ok");

        BusquedaHttp.Response response = BusquedaHttp.Response.builder()
                .id(busqueda.getId())
                .usuario(null)
                .contenido(busqueda.getContenido())
                .visitada(busqueda.getVisitada().toLocalDateTime())
                .estatus(busqueda.getEstatus())
                .creacion(busqueda.getCreacion().toLocalDateTime())
                .actualizacion(busqueda.getActualizacion().toLocalDateTime())
                .build();
        log.info("BusquedaService::crear::response::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("BusquedaService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Busqueda> busqueda = this.busquedaRepository.findById(id);
        if (busqueda.isEmpty()) {
            throw new Exception("No existe la busqueda");
        }

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        busqueda.get().setContenido(request.getContenido());
        busqueda.get().setEstatus(request.getEstatus());
        busqueda.get().setUsuario(usuario.get());
        busqueda.get().setVisitada(Timestamp.valueOf(request.getVisitada()));
        this.busquedaRepository.save(busqueda.get());
        log.info("BusquedaService::actualizar::save::Busqueda:{}", "ok");

        BusquedaHttp.Response response = BusquedaHttp.Response.builder()
                .id(busqueda.get().getId())
                .usuario(null)
                .contenido(busqueda.get().getContenido())
                .visitada(busqueda.get().getVisitada().toLocalDateTime())
                .estatus(busqueda.get().getEstatus())
                .creacion(busqueda.get().getCreacion().toLocalDateTime())
                .actualizacion(busqueda.get().getActualizacion().toLocalDateTime())
                .build();
        log.info("BusquedaService::actualizar::response::{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("BusquedaService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Busqueda> busqueda = this.busquedaRepository.findById(id);
        if (busqueda.isEmpty()) {
            throw new Exception("No existe la busqueda");
        }

        busqueda.get().setEstatus(false);
        this.busquedaRepository.save(busqueda.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio) {
        log.info("BusquedaService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Busqueda> busquedas = this.busquedaRepository.findAll(PageRequest.of(pagina, tamanio));

        PageResponse response = PageResponse.builder()
                .paginas(busquedas.getTotalPages())
                .paginaSeleccionada(busquedas.getNumber())
                .contenido(busquedas.getContent().stream()
                        .map(busqueda -> BusquedaHttp.Response.builder()
                                .id(busqueda.getId())
                                .usuario(null)
                                .contenido(busqueda.getContenido())
                                .visitada(busqueda.getVisitada().toLocalDateTime())
                                .estatus(busqueda.getEstatus())
                                .creacion(busqueda.getCreacion().toLocalDateTime())
                                .actualizacion(busqueda.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
