package com.amitravel.notificacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;
import com.amitravel.notificacion.NotificacionHttp.PageResponse;
import com.amitravel.notificacion.NotificacionHttp.Request;
import com.amitravel.notificacion.NotificacionHttp.Response;
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
public class NotificacionService {

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<Response> crear(Request request) throws Exception {
        log.info("NotificacionService::crear::lang:{}:request:{}", request);

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        List<Usuario> usuario = new ArrayList<Usuario>();
        if (request.getUsuarioIds() != null && !request.getUsuarioIds().isEmpty()) {
            usuario = this.usuarioRepository.findAllById(request.getUsuarioIds());
        }

        Notificacion nuevoNotificacion = new Notificacion();
        nuevoNotificacion.setDescripcion(request.getDescripcion());
        nuevoNotificacion.setEstatus(request.getEstatus());
        nuevoNotificacion.setImagen(request.getImagen());
        nuevoNotificacion.setNegocio(negocio.get());
        nuevoNotificacion.setNombre(request.getNombre());
        nuevoNotificacion.setTipo(request.getTipo());
        nuevoNotificacion.setUsuarios(usuario);
        Notificacion notificacion = this.notificacionRepository.save(nuevoNotificacion);
        log.info("NotificacionService::crear::save::nuevoNotificacion:{}", "ok");

        NotificacionHttp.Response response = NotificacionHttp.Response.builder()
                .id(notificacion.getId())
                .negocio(null)
                .usuarios(null)
                .nombre(notificacion.getNombre())
                .descripcion(notificacion.getDescripcion())
                .tipo(notificacion.getTipo())
                .imagen(notificacion.getImagen())
                .estatus(notificacion.getEstatus())
                .creacion(notificacion.getCreacion().toLocalDateTime())
                .actualizacion(notificacion.getActualizacion().toLocalDateTime())
                .build();
        log.info("NotificacionService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("NotificacionService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Notificacion> notificacion = this.notificacionRepository.findById(id);
        if (notificacion.isEmpty()) {
            throw new Exception("No existe la promocion");
        }
        Optional<Negocio> negocio = this.negocioRepository.findById(id);
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }
        List<Usuario> usuario = new ArrayList<Usuario>();
        if (request.getUsuarioIds() != null && !request.getUsuarioIds().isEmpty()) {
            usuario = this.usuarioRepository.findAllById(request.getUsuarioIds());
        }

        notificacion.get().setDescripcion(request.getDescripcion());
        notificacion.get().setEstatus(request.getEstatus());
        notificacion.get().setImagen(request.getImagen());
        notificacion.get().setNegocio(negocio.get());
        notificacion.get().setNombre(request.getNombre());
        notificacion.get().setTipo(request.getTipo());
        notificacion.get().setUsuarios(usuario);
        this.notificacionRepository.save(notificacion.get());
        log.info("NotificacionService::crear::save::nuevoNotificacion:{}", "ok");

        NotificacionHttp.Response response = NotificacionHttp.Response.builder()
                .id(notificacion.get().getId())
                .negocio(null)
                .usuarios(null)
                .nombre(notificacion.get().getNombre())
                .descripcion(notificacion.get().getDescripcion())
                .tipo(notificacion.get().getTipo())
                .imagen(notificacion.get().getImagen())
                .estatus(notificacion.get().getEstatus())
                .creacion(notificacion.get().getCreacion().toLocalDateTime())
                .actualizacion(notificacion.get().getActualizacion().toLocalDateTime())
                .build();
        log.info("NotificacionService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("NotificacionService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Notificacion> notificacion = this.notificacionRepository.findById(id);
        if (notificacion.isEmpty()) {
            throw new Exception("No existe la notificacion");
        }

        notificacion.get().setEstatus(false);
        this.notificacionRepository.save(notificacion.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long negocioId) {
        log.info("NotificacionService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Notificacion> notificaciones = null;

        if (negocioId != null) {
            if (busqueda != null) {
                notificaciones = this.notificacionRepository.findAllByNegocioIdAndMatch(negocioId, busqueda,
                        PageRequest.of(pagina, tamanio));
            } else {
                notificaciones = this.notificacionRepository.findAllByNegocioId(negocioId,
                        PageRequest.of(pagina, tamanio));
            }
        } else {
            if (busqueda != null) {
                notificaciones = this.notificacionRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
            } else {
                notificaciones = this.notificacionRepository.findAll(PageRequest.of(pagina, tamanio));
            }
        }

        PageResponse response = PageResponse.builder()
                .paginas(notificaciones.getTotalPages())
                .paginaSeleccionada(notificaciones.getNumber())
                .contenido(notificaciones.getContent().stream()
                        .map(notificacion -> NotificacionHttp.Response.builder()
                                .id(notificacion.getId())
                                .negocio(NegocioHttp.Response.builder()
                                        .id(notificacion.getNegocio().getId())
                                        .nombre(notificacion.getNegocio().getNombre())
                                        .descripcion(notificacion.getNegocio().getDescripcion())
                                        .imagen(notificacion.getNegocio().getImagen())
                                        .estatus(notificacion.getNegocio().getEstatus())
                                        .creacion(notificacion.getNegocio().getCreacion().toLocalDateTime())
                                        .actualizacion(notificacion.getNegocio().getActualizacion().toLocalDateTime())
                                        .build())
                                .usuarios(notificacion.getUsuarios().stream()
                                        .map(usuario -> UsuarioHttp.Response.builder()
                                                .id(usuario.getId())
                                                .apodo(usuario.getApodo())
                                                .contrasenia(usuario.getContrasenia())
                                                .rol(usuario.getRol())
                                                .estatus(usuario.getEstatus())
                                                .creacion(usuario.getCreacion().toLocalDateTime())
                                                .actualizacion(usuario.getActualizacion().toLocalDateTime())
                                                .build())
                                        .collect(Collectors.toList()))
                                .nombre(notificacion.getNombre())
                                .descripcion(notificacion.getDescripcion())
                                .tipo(notificacion.getTipo())
                                .imagen(notificacion.getImagen())
                                .estatus(notificacion.getEstatus())
                                .creacion(notificacion.getCreacion().toLocalDateTime())
                                .actualizacion(notificacion.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
