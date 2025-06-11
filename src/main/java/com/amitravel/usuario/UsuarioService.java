package com.amitravel.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;
import com.amitravel.usuario.UsuarioHttp.PageResponse;
import com.amitravel.usuario.UsuarioHttp.Request;
import com.amitravel.usuario.UsuarioHttp.Response;

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
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    public ResponseEntity<UsuarioHttp.Response> crear(String lang, UsuarioHttp.Request request) throws Exception {
        log.info("UsuarioService::crear::lang:{}:request:{}", lang, request);

        Optional<Usuario> usuarioOpt = this.usuarioRepository.findByApodo(request.getApodo());
        if (usuarioOpt.isPresent()) {
            throw new Exception("El apodo ya esta ocupado");
        }

        Optional<Negocio> negocio = (request.getNegocioId() != null)
                ? this.negocioRepository.findById(request.getNegocioId())
                : Optional.empty();

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setApodo(request.getApodo());
        nuevoUsuario.setContrasenia(request.getContrasenia());
        nuevoUsuario.setRol(request.getRol());
        nuevoUsuario.setEstatus(request.getEstatus());
        if (negocio.isPresent()) {
            nuevoUsuario.setNegocio(negocio.get());
        }
        Usuario usuario = this.usuarioRepository.save(nuevoUsuario);
        log.info("UsuarioService::crear::save::nuevousuario:{}", "ok");

        UsuarioHttp.Response response = UsuarioHttp.Response.builder()
                .id(usuario.getId())
                .apodo(usuario.getApodo())
                .contrasenia(usuario.getContrasenia())
                .rol(usuario.getRol())
                .estatus(usuario.getEstatus())
                .creacion(usuario.getCreacion().toLocalDateTime())
                .actualizacion(usuario.getActualizacion().toLocalDateTime())
                .persona(null)
                .agendas(null)
                .calificaciones(null)
                .busquedas(null)
                .aficiones(null)
                .notificaciones(null)
                .anuncios(null)
                .build();
        log.info("UsuarioService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("UsuarioService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new Exception("No existe el usuario");
        }

        if (!usuario.get().getApodo().equalsIgnoreCase(request.getApodo())) {
            Optional<Usuario> usuarioOpt = this.usuarioRepository.findByApodo(request.getApodo());
            if (usuarioOpt.isPresent()) {
                throw new Exception("El apodo ya esta ocupado");
            }
        }

        Optional<Negocio> negocio = (request.getNegocioId() != null)
                ? this.negocioRepository.findById(request.getNegocioId())
                : Optional.empty();

        usuario.get().setApodo(request.getApodo());
        usuario.get().setContrasenia(request.getContrasenia());
        usuario.get().setRol(request.getRol());
        usuario.get().setEstatus(request.getEstatus());
        if (negocio.isPresent()) {
            usuario.get().setNegocio(negocio.get());
        }
        this.usuarioRepository.save(usuario.get());
        log.info("UsuarioService::actualizar::save::usuario:{}", "ok");

        UsuarioHttp.Response response = UsuarioHttp.Response.builder()
                .id(usuario.get().getId())
                .apodo(usuario.get().getApodo())
                .contrasenia(usuario.get().getContrasenia())
                .rol(usuario.get().getRol())
                .estatus(usuario.get().getEstatus())
                .creacion(usuario.get().getCreacion().toLocalDateTime())
                .actualizacion(usuario.get().getActualizacion().toLocalDateTime())
                .persona(null)
                .agendas(null)
                .calificaciones(null)
                .busquedas(null)
                .aficiones(null)
                .notificaciones(null)
                .anuncios(null)
                .build();
        log.info("UsuarioService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("UsuarioService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isEmpty()) {
            throw new Exception("No existe el usuario");
        }

        usuario.get().setEstatus(false);
        this.usuarioRepository.save(usuario.get());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long id) {
        log.info("UsuarioService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio, busqueda);

        Page<Usuario> usuarios = null;
        if (busqueda != null) {
            usuarios = this.usuarioRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
        } else if (id != null) {
            Optional<Usuario> usuario = this.usuarioRepository.findById(id);
            usuarios = new PageImpl<Usuario>(List.of(usuario.get()), PageRequest.of(pagina, tamanio), 1L);
        } else {
            usuarios = this.usuarioRepository.findAll(PageRequest.of(pagina, tamanio));
        }

        PageResponse response = PageResponse.builder()
                .paginas(usuarios.getTotalPages())
                .paginaSeleccionada(usuarios.getNumber())
                .contenido(usuarios.getContent().stream()
                        .map(usuario -> UsuarioHttp.Response.builder()
                                .id(usuario.getId())
                                .apodo(usuario.getApodo())
                                .contrasenia(usuario.getContrasenia())
                                .rol(usuario.getRol())
                                .estatus(usuario.getEstatus())
                                .creacion(usuario.getCreacion().toLocalDateTime())
                                .actualizacion(usuario.getActualizacion().toLocalDateTime())
                                .persona(null)
                                .agendas(null)
                                .calificaciones(null)
                                .busquedas(null)
                                .aficiones(null)
                                .notificaciones(null)
                                .anuncios(null)
                                .negocio(usuario.getNegocio() != null ? NegocioHttp.Response.builder()
                                        .id(usuario.getNegocio().getId())
                                        .nombre(usuario.getNegocio().getNombre())
                                        .build() : null)
                                .build())
                        .collect(Collectors.toList()))
                .build();
        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<List<Response>> obtener(String lang) {
        log.info("UsuarioService::obtener::lang:{}", lang);

        List<Usuario> usuarios = this.usuarioRepository.findAll();

        List<UsuarioHttp.Response> response = usuarios.stream()
                .map(usuario -> UsuarioHttp.Response.builder()
                        .id(usuario.getId())
                        .apodo(usuario.getApodo())
                        .contrasenia(usuario.getContrasenia())
                        .rol(usuario.getRol())
                        .estatus(usuario.getEstatus())
                        .creacion(usuario.getCreacion().toLocalDateTime())
                        .actualizacion(usuario.getActualizacion().toLocalDateTime())
                        .persona(null)
                        .agendas(null)
                        .calificaciones(null)
                        .busquedas(null)
                        .aficiones(null)
                        .notificaciones(null)
                        .anuncios(null)
                        .negocio(usuario.getNegocio() != null ? NegocioHttp.Response.builder()
                                .id(usuario.getNegocio().getId())
                                .nombre(usuario.getNegocio().getNombre())
                                .build() : null)
                        .build())
                .collect(Collectors.toList());
        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
