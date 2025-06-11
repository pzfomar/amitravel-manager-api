package com.amitravel.calificacion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.calificacion.CalificacionHttp.PageResponse;
import com.amitravel.calificacion.CalificacionHttp.Request;
import com.amitravel.calificacion.CalificacionHttp.Response;
import com.amitravel.evento.Evento;
import com.amitravel.evento.EventoHttp;
import com.amitravel.evento.EventoRepository;
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
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public ResponseEntity<Response> crear(Request request) throws Exception {
        log.info("CalificacionService::crear::lang:{}:request:{}", request);

        Optional<Evento> evento = this.eventoRepository.findById(request.getEventoId());
        if (evento.isEmpty()) {
            throw new Exception("No existe evento");
        }
        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        Calificacion nuevoCalificacion = new Calificacion();
        nuevoCalificacion.setComentario(request.getComentario());
        nuevoCalificacion.setEstatus(request.getEstatus());
        nuevoCalificacion.setEvaluacion(request.getEvaluacion());
        nuevoCalificacion.setEvento(evento.get());
        nuevoCalificacion.setUsuario(usuario.get());
        Calificacion calificacion = this.calificacionRepository.save(nuevoCalificacion);
        log.info("CalificacionService::crear::save::nuevoCalificacion:{}", "ok");

        CalificacionHttp.Response response = CalificacionHttp.Response.builder()
                .id(calificacion.getId())
                .usuario(null)
                .evento(null)
                .evaluacion(calificacion.getEvaluacion())
                .comentario(calificacion.getComentario())
                .estatus(calificacion.getEstatus())
                .creacion(calificacion.getCreacion().toLocalDateTime())
                .actualizacion(calificacion.getActualizacion().toLocalDateTime())
                .build();

        log.info("CalificacionService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("CalificacionService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Calificacion> calificacion = this.calificacionRepository.findById(id);
        if (calificacion.isEmpty()) {
            throw new Exception("No existe la calificacion");
        }
        Optional<Evento> evento = this.eventoRepository.findById(request.getEventoId());
        if (evento.isEmpty()) {
            throw new Exception("No existe evento");
        }
        Optional<Usuario> usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (usuario.isEmpty()) {
            throw new Exception("No existe usuario");
        }

        calificacion.get().setComentario(request.getComentario());
        calificacion.get().setEstatus(request.getEstatus());
        calificacion.get().setEvaluacion(request.getEvaluacion());
        calificacion.get().setEvento(evento.get());
        calificacion.get().setUsuario(usuario.get());
        this.calificacionRepository.save(calificacion.get());
        log.info("CalificacionService::actualizar::save::Calificacion:{}", "ok");

        CalificacionHttp.Response response = CalificacionHttp.Response.builder()
                .id(calificacion.get().getId())
                .usuario(null)
                .evento(null)
                .evaluacion(calificacion.get().getEvaluacion())
                .comentario(calificacion.get().getComentario())
                .estatus(calificacion.get().getEstatus())
                .creacion(calificacion.get().getCreacion().toLocalDateTime())
                .actualizacion(calificacion.get().getActualizacion().toLocalDateTime())
                .build();

        log.info("CalificacionService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("CalificacionService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Calificacion> calificacion = this.calificacionRepository.findById(id);
        if (calificacion.isEmpty()) {
            throw new Exception("No existe la calificacion");
        }

        calificacion.get().setEstatus(false);
        this.calificacionRepository.save(calificacion.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long negocioId) {
        log.info("CalificacionService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio,
                busqueda);

        Page<Calificacion> calificaciones = null;
        if (negocioId != null) {
            if (busqueda != null) {
                calificaciones = this.calificacionRepository.findAllByEventoNegocioIdAndMatch(negocioId, busqueda,
                        PageRequest.of(pagina, tamanio));
            } else {
                calificaciones = this.calificacionRepository.findAllByEventoNegocioId(negocioId,
                        PageRequest.of(pagina, tamanio));
            }
        } else {
            if (busqueda != null) {
                calificaciones = this.calificacionRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
            } else {
                calificaciones = this.calificacionRepository.findAll(PageRequest.of(pagina, tamanio));
            }
        }

        PageResponse response = PageResponse.builder()
                .paginas(calificaciones.getTotalPages())
                .paginaSeleccionada(calificaciones.getNumber())
                .contenido(calificaciones.getContent().stream()
                        .map(calificacion -> CalificacionHttp.Response.builder()
                                .id(calificacion.getId())
                                .usuario(UsuarioHttp.Response.builder()
                                        .id(calificacion.getUsuario().getId())
                                        .apodo(calificacion.getUsuario().getApodo())
                                        .contrasenia(calificacion.getUsuario().getContrasenia())
                                        .rol(calificacion.getUsuario().getRol())
                                        .estatus(calificacion.getUsuario().getEstatus())
                                        .creacion(calificacion.getUsuario().getCreacion().toLocalDateTime())
                                        .actualizacion(calificacion.getUsuario().getActualizacion().toLocalDateTime())
                                        .build())
                                .evento(EventoHttp.Response.builder()
                                        .id(calificacion.getEvento().getId())
                                        .negocio(null)
                                        .nombre(calificacion.getEvento().getNombre())
                                        .descripcion(calificacion.getEvento().getDescripcion())
                                        .tipo(calificacion.getEvento().getTipo())
                                        .lugar(calificacion.getEvento().getLugar())
                                        .imagen(calificacion.getEvento().getImagen())
                                        .estatus(calificacion.getEvento().getEstatus())
                                        .creacion(calificacion.getEvento().getCreacion().toLocalDateTime())
                                        .actualizacion(calificacion.getEvento().getActualizacion().toLocalDateTime())
                                        .build())
                                .evaluacion(calificacion.getEvaluacion())
                                .comentario(calificacion.getComentario())
                                .estatus(calificacion.getEstatus())
                                .creacion(calificacion.getCreacion().toLocalDateTime())
                                .actualizacion(calificacion.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        log.info("CalificacionService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<Response>> obtener(String lang) {
        log.info("CalificacionService::obtener::lang:{}", lang);

        List<Calificacion> calificaciones = this.calificacionRepository.findAll();
        List<CalificacionHttp.Response> response = calificaciones.stream()
                .map(calificacion -> CalificacionHttp.Response.builder()
                        .id(calificacion.getId())
                        .usuario(UsuarioHttp.Response.builder()
                                .id(calificacion.getUsuario().getId())
                                .apodo(calificacion.getUsuario().getApodo())
                                .contrasenia(calificacion.getUsuario().getContrasenia())
                                .rol(calificacion.getUsuario().getRol())
                                .estatus(calificacion.getUsuario().getEstatus())
                                .creacion(calificacion.getUsuario().getCreacion().toLocalDateTime())
                                .actualizacion(calificacion.getUsuario().getActualizacion().toLocalDateTime())
                                .build())
                        .evento(EventoHttp.Response.builder()
                                .id(calificacion.getEvento().getId())
                                .negocio(null)
                                .nombre(calificacion.getEvento().getNombre())
                                .descripcion(calificacion.getEvento().getDescripcion())
                                .tipo(calificacion.getEvento().getTipo())
                                .lugar(calificacion.getEvento().getLugar())
                                .imagen(calificacion.getEvento().getImagen())
                                .estatus(calificacion.getEvento().getEstatus())
                                .creacion(calificacion.getEvento().getCreacion().toLocalDateTime())
                                .actualizacion(calificacion.getEvento().getActualizacion().toLocalDateTime())
                                .build())
                        .evaluacion(calificacion.getEvaluacion())
                        .comentario(calificacion.getComentario())
                        .estatus(calificacion.getEstatus())
                        .creacion(calificacion.getCreacion().toLocalDateTime())
                        .actualizacion(calificacion.getActualizacion().toLocalDateTime())
                        .build())
                .collect(Collectors.toList());

        log.info("CalificacionService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
