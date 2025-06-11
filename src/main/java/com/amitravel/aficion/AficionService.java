package com.amitravel.aficion;

import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.aficion.AficionHttp.PageResponse;
import com.amitravel.aficion.AficionHttp.Request;
import com.amitravel.aficion.AficionHttp.Response;
import com.amitravel.usuario.Usuario;
import com.amitravel.usuario.UsuarioHttp;
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
public class AficionService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AficionRepository aficionRepository;

    public ResponseEntity<AficionHttp.Response> crear(String lang, AficionHttp.Request request) throws Exception {
        log.info("AficionService::crear::lang:{}:request:{}", lang, request);

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        Aficion nuevoAficion = new Aficion();
        nuevoAficion.setEstatus(request.getEstatus());
        nuevoAficion.setHorarioActividad(request.getHorarioActividad());
        nuevoAficion.setMovilidad(request.getMovilidad());
        nuevoAficion.setPresupuesto(request.getPresupuesto());
        nuevoAficion.setSitio(request.getSitio());
        nuevoAficion.setUsuario(usuario.get());
        Aficion aficion = this.aficionRepository.save(nuevoAficion);
        log.info("AficionService::crear::save::nuevoProducto:{}", "ok");

        AficionHttp.Response response = AficionHttp.Response.builder()
                .id(aficion.getId())
                .usuario(
                        UsuarioHttp.Response.builder()
                                .id(aficion.getUsuario().getId())
                                .apodo(aficion.getUsuario().getApodo())
                                .contrasenia(aficion.getUsuario().getContrasenia())
                                .rol(aficion.getUsuario().getRol())
                                .estatus(aficion.getUsuario().getEstatus())
                                .creacion(aficion.getUsuario().getCreacion().toLocalDateTime())
                                .actualizacion(aficion.getUsuario().getActualizacion().toLocalDateTime())
                                .build())
                .movilidad(aficion.getMovilidad())
                .sitio(aficion.getSitio())
                .presupuesto(aficion.getPresupuesto())
                .horarioActividad(aficion.getHorarioActividad())
                .estatus(aficion.getEstatus())
                .creacion(aficion.getCreacion().toLocalDateTime())
                .actualizacion(aficion.getActualizacion().toLocalDateTime())
                .build();

        log.info("AficionService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("AficionService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Aficion> aficion = this.aficionRepository.findById(id);
        if (aficion.isEmpty()) {
            throw new Exception("No existe la aficion");
        }

        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        aficion.get().setEstatus(request.getEstatus());
        aficion.get().setHorarioActividad(request.getHorarioActividad());
        aficion.get().setMovilidad(request.getMovilidad());
        aficion.get().setPresupuesto(request.getPresupuesto());
        aficion.get().setSitio(request.getSitio());
        aficion.get().setUsuario(usuario.get());
        this.aficionRepository.save(aficion.get());
        log.info("AficionService::actualizar::save::Producto:{}", "ok");

        AficionHttp.Response response = AficionHttp.Response.builder()
                .id(aficion.get().getId())
                .usuario(
                        UsuarioHttp.Response.builder()
                                .id(aficion.get().getUsuario().getId())
                                .apodo(aficion.get().getUsuario().getApodo())
                                .contrasenia(aficion.get().getUsuario().getContrasenia())
                                .rol(aficion.get().getUsuario().getRol())
                                .estatus(aficion.get().getUsuario().getEstatus())
                                .creacion(aficion.get().getUsuario().getCreacion().toLocalDateTime())
                                .actualizacion(aficion.get().getUsuario().getActualizacion().toLocalDateTime())
                                .build())
                .movilidad(aficion.get().getMovilidad())
                .sitio(aficion.get().getSitio())
                .presupuesto(aficion.get().getPresupuesto())
                .horarioActividad(aficion.get().getHorarioActividad())
                .estatus(aficion.get().getEstatus())
                .creacion(aficion.get().getCreacion().toLocalDateTime())
                .actualizacion(aficion.get().getActualizacion().toLocalDateTime())
                .build();

        log.info("AficionService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("AficionService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Aficion> aficion = this.aficionRepository.findById(id);
        if (aficion.isEmpty()) {
            throw new Exception("No existe la aficion");
        }

        aficion.get().setEstatus(false);
        this.aficionRepository.save(aficion.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio) {
        log.info("AficionService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Aficion> aficiones = this.aficionRepository.findAll(PageRequest.of(pagina, tamanio));

        PageResponse response = PageResponse.builder()
                .paginas(aficiones.getTotalPages())
                .paginaSeleccionada(aficiones.getNumber())
                .contenido(aficiones.getContent().stream()
                        .map(aficion -> AficionHttp.Response.builder()
                                .id(aficion.getId())
                                .usuario(
                                        UsuarioHttp.Response.builder()
                                                .id(aficion.getUsuario().getId())
                                                .apodo(aficion.getUsuario().getApodo())
                                                .contrasenia(aficion.getUsuario().getContrasenia())
                                                .rol(aficion.getUsuario().getRol())
                                                .estatus(aficion.getUsuario().getEstatus())
                                                .creacion(aficion.getUsuario().getCreacion().toLocalDateTime())
                                                .actualizacion(
                                                        aficion.getUsuario().getActualizacion().toLocalDateTime())
                                                .build())
                                .movilidad(aficion.getMovilidad())
                                .sitio(aficion.getSitio())
                                .presupuesto(aficion.getPresupuesto())
                                .horarioActividad(aficion.getHorarioActividad())
                                .estatus(aficion.getEstatus())
                                .creacion(aficion.getCreacion().toLocalDateTime())
                                .actualizacion(aficion.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("AficionService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
