package com.amitravel.producto;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;

import com.amitravel.negocio.Negocio;
import com.amitravel.negocio.NegocioHttp;
import com.amitravel.negocio.NegocioRepository;
import com.amitravel.producto.ProductoHttp.PageResponse;
import com.amitravel.producto.ProductoHttp.Request;
import com.amitravel.producto.ProductoHttp.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    public ResponseEntity<ProductoHttp.Response> crear(String lang, ProductoHttp.Request request) throws Exception {
        log.info("ProductoService::crear::lang:{}:request:{}", lang, request);

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        Producto nuevoProducto = new Producto();
        nuevoProducto.setNegocio(negocio.get());
        nuevoProducto.setNombre(request.getNombre());
        nuevoProducto.setTipo(request.getTipo());
        nuevoProducto.setDescripcion(request.getDescripcion());
        nuevoProducto.setImagen(request.getImagen());
        nuevoProducto.setCaducidad(Timestamp.valueOf(request.getCaducidad()));
        nuevoProducto.setCantidad(request.getCantidad());
        nuevoProducto.setEstatus(request.getEstatus());
        Producto producto = this.productoRepository.save(nuevoProducto);
        log.info("ProductoService::crear::save::nuevoProducto:{}", "ok");

        ProductoHttp.Response response = ProductoHttp.Response.builder()
                .id(producto.getId())
                .negocio(null)
                .nombre(producto.getNombre())
                .tipo(producto.getTipo())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .caducidad(producto.getCaducidad().toLocalDateTime())
                .cantidad(producto.getCantidad())
                .estatus(producto.getEstatus())
                .creacion(producto.getCreacion().toLocalDateTime())
                .actualizacion(producto.getActualizacion().toLocalDateTime())
                .build();
        log.info("ProductoService::crear::response:{}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<Response> actualizar(String lang, Long id, Request request) throws Exception {
        log.info("ProductoService::actualizar::lang:{}:id:{}:request:{}", lang, id, request);

        Optional<Producto> producto = this.productoRepository.findById(id);
        if (producto.isEmpty()) {
            throw new Exception("No existe la producto");
        }

        Optional<Negocio> negocio = this.negocioRepository.findById(request.getNegocioId());
        if (negocio.isEmpty()) {
            throw new Exception("No existe negocio");
        }

        producto.get().setNegocio(negocio.get());
        producto.get().setNombre(request.getNombre());
        producto.get().setTipo(request.getTipo());
        producto.get().setDescripcion(request.getDescripcion());
        producto.get().setImagen(request.getImagen());
        producto.get().setCaducidad(Timestamp.valueOf(request.getCaducidad()));
        producto.get().setCantidad(request.getCantidad());
        producto.get().setEstatus(request.getEstatus());
        this.productoRepository.save(producto.get());
        log.info("ProductoService::actualizar::save::producto:{}", "ok");

        ProductoHttp.Response response = ProductoHttp.Response.builder()
                .id(producto.get().getId())
                .negocio(null)
                .nombre(producto.get().getNombre())
                .tipo(producto.get().getTipo())
                .descripcion(producto.get().getDescripcion())
                .imagen(producto.get().getImagen())
                .caducidad(producto.get().getCaducidad().toLocalDateTime())
                .cantidad(producto.get().getCantidad())
                .estatus(producto.get().getEstatus())
                .creacion(producto.get().getCreacion().toLocalDateTime())
                .actualizacion(producto.get().getActualizacion().toLocalDateTime())
                .build();
        log.info("ProductoService::actualizar::response:{}", response);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    public ResponseEntity<Response> eliminar(String lang, Long id) throws Exception {
        log.info("ProductoService::eliminar::lang:{}:id:{}", lang, id);

        Optional<Producto> producto = this.productoRepository.findById(id);
        if (producto.isEmpty()) {
            throw new Exception("No existe el producto");
        }

        producto.get().setEstatus(false);
        this.productoRepository.save(producto.get());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    public ResponseEntity<PageResponse> obtener(String lang, Integer pagina, Integer tamanio, String busqueda,
            Long negocioId) {
        log.info("ProductoService::obtener::lang:{}:pagina:{}:tamanio:{}", lang, pagina, tamanio);

        Page<Producto> productos = null;

        if (negocioId != null) {
            if (busqueda != null) {
                productos = this.productoRepository.findAllByNegocioIdAndMatch(negocioId, busqueda,
                        PageRequest.of(pagina, tamanio));
            } else {
                productos = this.productoRepository.findAllByNegocioId(negocioId, PageRequest.of(pagina, tamanio));
            }
        } else {
            if (busqueda != null) {
                productos = this.productoRepository.findAllByMatch(busqueda, PageRequest.of(pagina, tamanio));
            } else {
                productos = this.productoRepository.findAll(PageRequest.of(pagina, tamanio));
            }
        }

        PageResponse response = PageResponse.builder()
                .paginas(productos.getTotalPages())
                .paginaSeleccionada(productos.getNumber())
                .contenido(productos.getContent().stream()
                        .map(producto -> ProductoHttp.Response.builder()
                                .id(producto.getId())
                                .negocio(NegocioHttp.Response.builder()
                                        .id(producto.getNegocio().getId())
                                        .nombre(producto.getNegocio().getNombre())
                                        .descripcion(producto.getNegocio().getDescripcion())
                                        .imagen(producto.getNegocio().getImagen())
                                        .estatus(producto.getNegocio().getEstatus())
                                        .creacion(producto.getNegocio().getCreacion().toLocalDateTime())
                                        .actualizacion(producto.getNegocio().getActualizacion().toLocalDateTime())
                                        .build())
                                .nombre(producto.getNombre())
                                .tipo(producto.getTipo())
                                .descripcion(producto.getDescripcion())
                                .imagen(producto.getImagen())
                                .caducidad(producto.getCaducidad().toLocalDateTime())
                                .cantidad(producto.getCantidad())
                                .estatus(producto.getEstatus())
                                .creacion(producto.getCreacion().toLocalDateTime())
                                .actualizacion(producto.getActualizacion().toLocalDateTime())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        log.info("UsuarioService::obtener::response:{}", response);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
