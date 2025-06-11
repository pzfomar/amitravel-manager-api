package com.amitravel.anuncio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.anuncio.AnuncioHttp.PageResponse;
import com.amitravel.anuncio.AnuncioHttp.Request;
import com.amitravel.anuncio.AnuncioHttp.Response;
import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;
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
public class AnuncioService {
        @Autowired
        private NegocioRepository negocioRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private AnuncioRepository anuncioRepository;

        public ResponseEntity<AnuncioHttp.Response> crear(String lang, AnuncioHttp.Request request) throws Exception {
                log.info("AnuncioService::crear::lang:{}:request:{}", lang, request);

                Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
                if (negocio.isEmpty()) {
                        throw new Exception("No existe el negocio");
                }

                List<Usuario> usuarios = new ArrayList<Usuario>();
                if (request.getUsuarioIds() != null && !request.getUsuarioIds().isEmpty()) {
                        usuarios = this.usuarioRepository.findAllById(request.getUsuarioIds());
                }

                Anuncio nuevoAnuncio = new Anuncio();
                nuevoAnuncio.setDescripcion(request.getDescripcion());
                nuevoAnuncio.setEstatus(request.getEstatus());
                nuevoAnuncio.setImagen(request.getImagen());
                nuevoAnuncio.setNegocio(negocio.get());
                nuevoAnuncio.setNombre(request.getNombre());
                nuevoAnuncio.setUrl(request.getUrl());
                nuevoAnuncio.setUsuarios(usuarios);
                Anuncio anuncio = this.anuncioRepository.save(nuevoAnuncio);
                log.info("AnuncioService::crear::save::nuevoAnuncio:{}", "ok");

                AnuncioHttp.Response response = AnuncioHttp.Response.builder()
                                .id(anuncio.getId())
                                .negocio(NegocioHttp.Response.builder()
                                                .id(anuncio.getNegocio().getId())
                                                .nombre(anuncio.getNegocio().getNombre())
                                                .descripcion(anuncio.getNegocio().getDescripcion())
                                                .imagen(anuncio.getNegocio().getImagen())
                                                .estatus(anuncio.getNegocio().getEstatus())
                                                .creacion(anuncio.getNegocio().getCreacion().toLocalDateTime())
                                                .actualizacion(anuncio.getNegocio().getActualizacion()
                                                                .toLocalDateTime())
                                                .build())
                                .usuarios(anuncio.getUsuarios().stream()
                                                .map(usuario -> UsuarioHttp.Response.builder()
                                                                .id(usuario.getId())
                                                                .apodo(usuario.getApodo())
                                                                .contrasenia(usuario.getContrasenia())
                                                                .rol(usuario.getRol())
                                                                .estatus(usuario.getEstatus())
                                                                .creacion(usuario.getCreacion().toLocalDateTime())
                                                                .actualizacion(usuario.getActualizacion()
                                                                                .toLocalDateTime())
                                                                .build())
                                                .collect(Collectors.toList()))
                                .nombre(anuncio.getNombre())
                                .descripcion(anuncio.getDescripcion())
                                .imagen(anuncio.getImagen())
                                .url(anuncio.getUrl())
                                .estatus(anuncio.getEstatus())
                                .creacion(anuncio.getCreacion().toLocalDateTime())
                                .actualizacion(anuncio.getActualizacion().toLocalDateTime())
                                .build();
                log.info("AnuncioService::crear::response:{}", response);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
                log.info("AnuncioService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

                Optional<Anuncio> anuncio = this.anuncioRepository.findById(id);
                if (anuncio.isEmpty()) {
                        throw new Exception("No existe el anuncio");
                }

                Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
                if (negocio.isEmpty()) {
                        throw new Exception("No existe el negocio");
                }

                List<Usuario> usuarios = new ArrayList<Usuario>();
                if (request.getUsuarioIds() != null && !request.getUsuarioIds().isEmpty()) {
                        usuarios = this.usuarioRepository.findAllById(request.getUsuarioIds());
                }

                anuncio.get().setDescripcion(request.getDescripcion());
                anuncio.get().setEstatus(request.getEstatus());
                anuncio.get().setImagen(request.getImagen());
                anuncio.get().setNegocio(negocio.get());
                anuncio.get().setNombre(request.getNombre());
                anuncio.get().setUrl(request.getUrl());
                anuncio.get().setUsuarios(usuarios);
                this.anuncioRepository.save(anuncio.get());
                log.info("AnuncioService::actualizar::save::Anuncio:{}", "ok");

                AnuncioHttp.Response response = AnuncioHttp.Response.builder()
                                .id(anuncio.get().getId())
                                .negocio(NegocioHttp.Response.builder()
                                                .id(anuncio.get().getNegocio().getId())
                                                .nombre(anuncio.get().getNegocio().getNombre())
                                                .descripcion(anuncio.get().getNegocio().getDescripcion())
                                                .imagen(anuncio.get().getNegocio().getImagen())
                                                .estatus(anuncio.get().getNegocio().getEstatus())
                                                .creacion(anuncio.get().getNegocio().getCreacion().toLocalDateTime())
                                                .actualizacion(anuncio.get().getNegocio().getActualizacion()
                                                                .toLocalDateTime())
                                                .build())
                                .usuarios(anuncio.get().getUsuarios().stream()
                                                .map(usuario -> UsuarioHttp.Response.builder()
                                                                .id(usuario.getId())
                                                                .apodo(usuario.getApodo())
                                                                .contrasenia(usuario.getContrasenia())
                                                                .rol(usuario.getRol())
                                                                .estatus(usuario.getEstatus())
                                                                .creacion(usuario.getCreacion().toLocalDateTime())
                                                                .actualizacion(usuario.getActualizacion()
                                                                                .toLocalDateTime())
                                                                .build())
                                                .collect(Collectors.toList()))
                                .nombre(anuncio.get().getNombre())
                                .descripcion(anuncio.get().getDescripcion())
                                .imagen(anuncio.get().getImagen())
                                .url(anuncio.get().getUrl())
                                .estatus(anuncio.get().getEstatus())
                                .creacion(anuncio.get().getCreacion().toLocalDateTime())
                                .actualizacion(anuncio.get().getActualizacion().toLocalDateTime())
                                .build();
                log.info("AnuncioService::actualizar::response:{}", response);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }

        public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
                log.info("AnuncioService::eliminar::lang:{}:id:{}", lang, id);

                Optional<Anuncio> anuncio = this.anuncioRepository.findById(id);
                if (anuncio.isEmpty()) {
                        throw new Exception("No existe el anuncio");
                }

                anuncio.get().setEstatus(false);
                this.anuncioRepository.save(anuncio.get());

                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
                        Long negocioId) {
                log.info("AnuncioService::obtener::lang:{}:pagina:{}:tamanio:{}:busqueda:{}", lang, pagina, tamanio,
                                busqueda);

                Page<Anuncio> anuncios = null;

                if (negocioId != null) {
                        if (busqueda != null) {
                                anuncios = this.anuncioRepository.findAllByNegocioIdAndMatch(negocioId, busqueda,
                                                PageRequest.of(pagina, tamanio));
                        } else {
                                anuncios = this.anuncioRepository.findAllByNegocioId(negocioId,
                                                PageRequest.of(pagina, tamanio));
                        }
                } else {
                        if (busqueda != null) {
                                anuncios = this.anuncioRepository.findAllByMatch(busqueda,
                                                PageRequest.of(pagina, tamanio));
                        } else {
                                anuncios = this.anuncioRepository.findAll(PageRequest.of(pagina, tamanio));
                        }
                }

                PageResponse response = PageResponse.builder()
                                .paginas(anuncios.getTotalPages())
                                .paginaSeleccionada(anuncios.getNumber())
                                .contenido(anuncios.getContent().stream()
                                                .map(anuncio -> AnuncioHttp.Response.builder()
                                                                .id(anuncio.getId())
                                                                .negocio(NegocioHttp.Response.builder()
                                                                                .id(anuncio.getNegocio().getId())
                                                                                .nombre(anuncio.getNegocio()
                                                                                                .getNombre())
                                                                                .descripcion(anuncio.getNegocio()
                                                                                                .getDescripcion())
                                                                                .imagen(anuncio.getNegocio()
                                                                                                .getImagen())
                                                                                .estatus(anuncio.getNegocio()
                                                                                                .getEstatus())
                                                                                .creacion(anuncio.getNegocio()
                                                                                                .getCreacion()
                                                                                                .toLocalDateTime())
                                                                                .actualizacion(anuncio.getNegocio()
                                                                                                .getActualizacion()
                                                                                                .toLocalDateTime())
                                                                                .build())
                                                                .usuarios(anuncio.getUsuarios().stream()
                                                                                .map(usuario -> UsuarioHttp.Response
                                                                                                .builder()
                                                                                                .id(usuario.getId())
                                                                                                .apodo(usuario.getApodo())
                                                                                                .contrasenia(usuario
                                                                                                                .getContrasenia())
                                                                                                .rol(usuario.getRol())
                                                                                                .estatus(usuario.getEstatus())
                                                                                                .creacion(usuario
                                                                                                                .getCreacion()
                                                                                                                .toLocalDateTime())
                                                                                                .actualizacion(usuario
                                                                                                                .getActualizacion()
                                                                                                                .toLocalDateTime())
                                                                                                .build())
                                                                                .collect(Collectors.toList()))
                                                                .nombre(anuncio.getNombre())
                                                                .descripcion(anuncio.getDescripcion())
                                                                .imagen(anuncio.getImagen())
                                                                .url(anuncio.getUrl())
                                                                .estatus(anuncio.getEstatus())
                                                                .creacion(anuncio.getCreacion().toLocalDateTime())
                                                                .actualizacion(anuncio.getActualizacion()
                                                                                .toLocalDateTime())
                                                                .build())
                                                .collect(Collectors.toList()))
                                .build();
                log.info("AnuncioService::obtener::response:{}", response);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        public ResponseEntity<List<Response>> obtener(String lang) {
                log.info("AnuncioService::obtener::lang:{}", lang);

                List<Anuncio> anuncios = this.anuncioRepository.findAll();

                List<AnuncioHttp.Response> response = anuncios.stream()
                                .map(anuncio -> AnuncioHttp.Response.builder()
                                                .id(anuncio.getId())
                                                .negocio(NegocioHttp.Response.builder()
                                                                .id(anuncio.getNegocio().getId())
                                                                .nombre(anuncio.getNegocio().getNombre())
                                                                .descripcion(anuncio.getNegocio().getDescripcion())
                                                                .imagen(anuncio.getNegocio().getImagen())
                                                                .estatus(anuncio.getNegocio().getEstatus())
                                                                .creacion(anuncio.getNegocio().getCreacion()
                                                                                .toLocalDateTime())
                                                                .actualizacion(anuncio.getNegocio().getActualizacion()
                                                                                .toLocalDateTime())
                                                                .build())
                                                .usuarios(anuncio.getUsuarios().stream()
                                                                .map(usuario -> UsuarioHttp.Response.builder()
                                                                                .id(usuario.getId())
                                                                                .apodo(usuario.getApodo())
                                                                                .contrasenia(usuario.getContrasenia())
                                                                                .rol(usuario.getRol())
                                                                                .estatus(usuario.getEstatus())
                                                                                .creacion(usuario.getCreacion()
                                                                                                .toLocalDateTime())
                                                                                .actualizacion(usuario
                                                                                                .getActualizacion()
                                                                                                .toLocalDateTime())
                                                                                .build())
                                                                .collect(Collectors.toList()))
                                                .nombre(anuncio.getNombre())
                                                .descripcion(anuncio.getDescripcion())
                                                .imagen(anuncio.getImagen())
                                                .url(anuncio.getUrl())
                                                .estatus(anuncio.getEstatus())
                                                .creacion(anuncio.getCreacion().toLocalDateTime())
                                                .actualizacion(anuncio.getActualizacion().toLocalDateTime())
                                                .build())
                                .collect(Collectors.toList());

                log.info("AnuncioService::obtener::response:{}", response);

                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

}
