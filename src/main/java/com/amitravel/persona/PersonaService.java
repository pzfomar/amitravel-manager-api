package com.amitravel.persona;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.persona.PersonaHttp.PageResponse;
import com.amitravel.persona.PersonaHttp.Request;
import com.amitravel.persona.PersonaHttp.Response;
import com.amitravel.usuario.Usuario;
import com.amitravel.usuario.UsuarioHttp;
import com.amitravel.usuario.UsuarioRepository;

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
public class PersonaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PersonaRepository personaRepository;

    public ResponseEntity<PersonaHttp.Response> crear(String lang, PersonaHttp.Request request) throws Exception {
        log.info("PersonaService::crear::lang:{}:request:{}", lang, request);

        Optional<Usuario> Usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (Usuario.isEmpty()) {
            throw new Exception("No existe Usuario");
        }

        Persona nuevoPersona = new Persona();
        nuevoPersona.setCorreo(request.getCorreo());
        nuevoPersona.setApellidoMaterno(request.getApellidoMaterno());
        nuevoPersona.setApellidoPaterno(request.getApellidoPaterno());
        nuevoPersona.setEdad(request.getEdad());
        nuevoPersona.setEstatus(request.getEstatus());
        nuevoPersona.setNombre(request.getNombre());
        nuevoPersona.setTelefono(request.getTelefono());
        nuevoPersona.setUsuario(Usuario.get());
        Persona persona = this.personaRepository.save(nuevoPersona);
        log.info("PersonaService::crear::save::nuevoPersona:{}", "ok");

        PersonaHttp.Response response = PersonaHttp.Response.builder()
                .id(persona.getId())
                .usuario(null)
                .correo(persona.getCorreo())
                .nombre(persona.getNombre())
                .apellidoPaterno(persona.getApellidoPaterno())
                .apellidoMaterno(persona.getApellidoMaterno())
                .edad(persona.getEdad())
                .telefono(persona.getTelefono())
                .estatus(persona.getEstatus())
                .creacion(persona.getCreacion().toLocalDateTime())
                .actualizacion(persona.getActualizacion().toLocalDateTime())
                .build();
        log.info("PersonaService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("PersonaService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Persona> persona = this.personaRepository.findById(id);
        if (persona.isEmpty()) {
            throw new Exception("No existe la Persona");
        }

        Optional<Usuario> Usuario = this.usuarioRepository.findById(request.getUsuarioId());
        if (Usuario.isEmpty()) {
            throw new Exception("No existe Usuario");
        }

        persona.get().setCorreo(request.getCorreo());
        persona.get().setApellidoMaterno(request.getApellidoMaterno());
        persona.get().setApellidoPaterno(request.getApellidoPaterno());
        persona.get().setEdad(request.getEdad());
        persona.get().setEstatus(request.getEstatus());
        persona.get().setNombre(request.getNombre());
        persona.get().setTelefono(request.getTelefono());
        persona.get().setUsuario(Usuario.get());
        this.personaRepository.save(persona.get());
        log.info("PersonaService::actualizar::save::nuevoPersona:{}", "ok");

        PersonaHttp.Response response = PersonaHttp.Response.builder()
                .id(persona.get().getId())
                .usuario(null)
                .correo(persona.get().getCorreo())
                .nombre(persona.get().getNombre())
                .apellidoPaterno(persona.get().getApellidoPaterno())
                .apellidoMaterno(persona.get().getApellidoMaterno())
                .edad(persona.get().getEdad())
                .telefono(persona.get().getTelefono())
                .estatus(persona.get().getEstatus())
                .creacion(persona.get().getCreacion().toLocalDateTime())
                .actualizacion(persona.get().getActualizacion().toLocalDateTime())
                .build();
        log.info("PersonaService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("PersonaService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Persona> persona = this.personaRepository.findById(id);
        if (persona.isEmpty()) {
            throw new Exception("No existe la persona");
        }

        persona.get().setEstatus(false);
        this.personaRepository.save(persona.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long usuarioId) {
        log.info("PersonaService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Persona> personas = null;
        if (busqueda != null) {
            personas = this.personaRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
        } else if (usuarioId != null) {
            Optional<Persona> persona = this.personaRepository.findByUsuarioId(usuarioId);
            personas = new PageImpl<Persona>(List.of(persona.get()), PageRequest.of(pagina, tamanio), 1L);
        } else {
            personas = this.personaRepository.findAll(PageRequest.of(pagina, tamanio));
        }

        PageResponse response = PageResponse.builder()
                .paginas(personas.getTotalPages())
                .paginaSeleccionada(personas.getNumber())
                .contenido(personas.getContent().stream()
                        .map(persona -> PersonaHttp.Response.builder()
                                .id(persona.getId())
                                .usuario(UsuarioHttp.Response.builder()
                                        .id(persona.getUsuario().getId())
                                        .apodo(persona.getUsuario().getApodo())
                                        .contrasenia(persona.getUsuario().getContrasenia())
                                        .rol(persona.getUsuario().getRol())
                                        .estatus(persona.getUsuario().getEstatus())
                                        .creacion(persona.getUsuario().getCreacion().toLocalDateTime())
                                        .actualizacion(persona.getUsuario().getActualizacion().toLocalDateTime())
                                        .build())
                                .correo(persona.getCorreo())
                                .nombre(persona.getNombre())
                                .apellidoPaterno(persona.getApellidoPaterno())
                                .apellidoMaterno(persona.getApellidoMaterno())
                                .edad(persona.getEdad())
                                .telefono(persona.getTelefono())
                                .estatus(persona.getEstatus())
                                .creacion(persona.getCreacion().toLocalDateTime())
                                .actualizacion(persona.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
