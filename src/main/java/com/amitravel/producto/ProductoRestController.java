package com.amitravel.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("{lang}/producto")
public class ProductoRestController {
    @Autowired
    private ProductoService productoService;

    @PostMapping(path = "")
    public ResponseEntity<ProductoHttp.Response> crear(@PathVariable("lang") String lang,
            @RequestBody ProductoHttp.Request request) throws Exception {
        return this.productoService.crear(lang, request);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ProductoHttp.Response> actualizar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id, @RequestBody ProductoHttp.Request request) throws Exception {
        return this.productoService.actualizar(lang, id, request);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ProductoHttp.Response> eliminar(@PathVariable("lang") String lang,
            @PathVariable("id") Long id) throws Exception {
        return this.productoService.eliminar(lang, id);
    }

    @GetMapping(path = "")
    public ResponseEntity<ProductoHttp.PageResponse> obtener(@PathVariable("lang") String lang,
            @RequestParam(name = "pagina", defaultValue = "0", required = true) Integer pagina,
            @RequestParam(name = "tamanio", defaultValue = "5", required = true) Integer tamanio,
            @RequestParam(name = "busqueda", required = false) String busqueda,
            @RequestParam(name = "negocioId", required = false) Long negocioId) throws Exception {
        return this.productoService.obtener(lang, pagina, tamanio, busqueda, negocioId);
    }
}
